package com.raproject.whattowatch.repository.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.raproject.whattowatch.BuildConfig
import com.raproject.whattowatch.models.AppPurchase
import com.raproject.whattowatch.repository.use_case.GetPurchaseTimeUseCase
import com.raproject.whattowatch.repository.use_case.SetPurchaseUseCase
import com.raproject.whattowatch.utils.AppErrorHandler.logError
import com.raproject.whattowatch.utils.AppErrorHandler.logMessage
import com.raproject.whattowatch.utils.createQueryProductDetailsParamsForSubscription
import com.raproject.whattowatch.utils.getExpirationTime
import com.raproject.whattowatch.utils.getProductId
import com.raproject.whattowatch.utils.throwIfNotSuccessful
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Instant

class BillingCore(private val context: Context) : BillingClientStateListener, KoinComponent {

    private val getPurchaseTimeUseCase: GetPurchaseTimeUseCase by inject()
    private val setPurchaseTimeUseCase: SetPurchaseUseCase by inject()

    val isSubscriptionEnable = MutableStateFlow(getPurchaseTimeUseCase.invoke() > Instant.now())
    val isSetupFinished = MutableStateFlow(false)

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var isConnected: Boolean = false

    private val subscriptionList = listOf(
        BuildConfig.ANNUALLY_SUBSCRIPTION,
        BuildConfig.MOUNTHLY_SUBSCRIPTION,
        BuildConfig.THREE_MOUNTHS_SUBSCRIPTION
    )

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            scope.launch { purchases?.handle { isSubscriptionEnable.emit(it) } }
        }
    }


    private suspend fun startConnection() = withContext(Dispatchers.Default) {
        billingClient.startConnection(this@BillingCore)
    }


    private val queryProductDetailsParams by lazy {
        QueryProductDetailsParams.newBuilder().setProductList(
            subscriptionList.createQueryProductDetailsParamsForSubscription()
        ).build()
    }

    private val queryPurchaseParams by lazy {
        QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS).build()
    }

    private lateinit var billingClient: BillingClient
    private val availablePurchase = MutableStateFlow<List<AppPurchase>>(listOf())

    fun init() {
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
        billingClient.startConnection(this@BillingCore)
    }

    override fun onBillingServiceDisconnected() {
        isConnected = false
        logMessage(text = "onBillingServiceDisconnected")
        scope.launch(Dispatchers.Main) {
            delay(5000)
            startConnection()
        }
    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        logMessage(text = "onBillingSetupFinished: ${billingResult.responseCode}")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            isConnected = true

            scope.launch {
                updateAvailablePurchases()
                handlePurchasedSubs()
            }

        } else scope.launch { isSetupFinished.emit(true) }
    }

    private suspend fun updateAvailablePurchases() = withContext(Dispatchers.Default) {

        runCatching {
            val params = queryProductDetailsParams
            // leverage queryProductDetails Kotlin extension function
            val result = withContext(Dispatchers.IO) {
                billingClient.queryProductDetails(params)
            }
            if (result.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                result.productDetailsList ?: listOf()
            } else {
                throw Throwable("Result: ${result.billingResult.responseCode}, ${result.billingResult.debugMessage}")
            }
        }.onSuccess {
            availablePurchase.emit(it.map { AppPurchase(it) })
        }.onFailure {
            it.logError()
            availablePurchase.emit(listOf())
        }
    }

    private suspend fun handlePurchasedSubs() = withContext(Dispatchers.Default) {

        runCatching {
            billingClient.queryPurchasesAsync(queryPurchaseParams) { code, details ->
                var subscriptionStatus = false
                if (code.responseCode == BillingClient.BillingResponseCode.OK) {
                    runBlocking { details.handle { subscriptionStatus = it } }
                }
                scope.launch {
                    isSubscriptionEnable.emit(subscriptionStatus)
                    isSetupFinished.emit(true)
                }
            }
        }.onSuccess {

        }.onFailure {
            isSubscriptionEnable.emit(false)
            it.logError()
        }
    }

    private suspend fun List<Purchase>.handle(onFindPurchased: suspend (Boolean) -> Unit) {
        var subscriptionStatus = false
        forEach { purchase ->
            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                if (purchase.isAcknowledged.not()) acknowledge(purchase)

                val productId = purchase.getProductId(purchaseIdList = subscriptionList)

                val purchaseTime = Instant.ofEpochSecond(purchase.purchaseTime)

                val expirationTime = purchaseTime.getExpirationTime(productId)

                setPurchaseTimeUseCase.invoke(expirationTime)

                subscriptionStatus = true
            }
        }
        onFindPurchased.invoke(subscriptionStatus)
    }

    private suspend fun acknowledge(purchase: Purchase) {
        billingClient.acknowledgePurchase(
            AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        )
    }

    private val billingMutex = Mutex()

    suspend fun handleBilling(activity: Activity, purchase: AppPurchase): Deferred<Result<Unit>> =
        billingMutex.withLock {
            withContext(Dispatchers.Default) {
                async { launchBulling(activity, purchase = purchase) }
            }
        }


    private suspend fun launchBulling(activity: Activity, purchase: AppPurchase) = runCatching {
        if (isSubscriptionEnable.value) throw Throwable("subscription already purchased")

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(purchase.productDetails)
                .setOfferToken(purchase.productDetails.subscriptionOfferDetails.leastPricedOfferToken())
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        withContext(Dispatchers.IO) {
            billingClient.launchBillingFlow(activity, billingFlowParams)
        }.throwIfNotSuccessful()
    }


    private fun List<ProductDetails.SubscriptionOfferDetails>?.leastPricedOfferToken(): String {
        var offerToken = String()
        var leastPricedOffer: ProductDetails.SubscriptionOfferDetails
        var lowestPrice = Int.MAX_VALUE

        if (isNullOrEmpty()) return offerToken

        forEach { offer ->
            offer.pricingPhases.pricingPhaseList.forEach { price ->
                if (price.priceAmountMicros < lowestPrice) {
                    lowestPrice = price.priceAmountMicros.toInt()
                    leastPricedOffer = offer
                    offerToken = leastPricedOffer.offerToken
                }
            }
        }

        return offerToken
    }


    class BillingError(result: BillingResult) :
        Throwable("code: ${result.responseCode}, ${result.debugMessage}")
}