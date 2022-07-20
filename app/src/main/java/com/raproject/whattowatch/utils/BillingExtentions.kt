package com.raproject.whattowatch.utils

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.raproject.whattowatch.BuildConfig
import com.raproject.whattowatch.repository.billing.BillingCore
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

fun BillingResult.isSuccessful(): Boolean {
    return this.responseCode == BillingClient.BillingResponseCode.OK
}

fun BillingResult.throwIfNotSuccessful() {
    return if (isSuccessful().not()) throw BillingCore.BillingError(this) else Unit
}

fun Purchase.getProductId(purchaseIdList: List<String>): String {
    return products.find { id -> purchaseIdList.contains(id) } ?: ""
}

fun Instant.getExpirationTime(productId: String): Instant {
    val localTime = LocalDateTime.ofInstant(this, ZoneId.systemDefault())

    return when (productId) {
        BuildConfig.ANNUALLY_SUBSCRIPTION -> localTime.plusYears(1)
        BuildConfig.MOUNTHLY_SUBSCRIPTION -> localTime.plusMonths(1)
        BuildConfig.THREE_MOUNTHS_SUBSCRIPTION -> localTime.plusMonths(3)
        else -> localTime
    }.toInstant(ZoneOffset.UTC)
}