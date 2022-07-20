package com.raproject.whattowatch.repository.billing

import android.app.Activity
import com.raproject.whattowatch.models.AppPurchase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BillingManager() : KoinComponent {

    private val billingCore: BillingCore by inject()

    val isSubscriptionEnable: MutableStateFlow<Boolean>
        get() = billingCore.isSubscriptionEnable

    fun init() {
        billingCore.init()
    }

    suspend fun handleBilling(activity: Activity, purchase: AppPurchase): Deferred<Result<Unit>> {
        return billingCore.handleBilling(activity, purchase)
    }
}