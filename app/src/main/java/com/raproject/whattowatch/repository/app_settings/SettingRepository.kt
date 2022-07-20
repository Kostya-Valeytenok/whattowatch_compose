package com.raproject.whattowatch.repository.app_settings

import com.raproject.whattowatch.repository.Repository
import com.raproject.whattowatch.utils.AppState
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.OrderType
import com.raproject.whattowatch.utils.TableRow
import java.time.Instant

class SettingRepository(private val settings: AppSettings) : Repository() {

    fun getLocalization(): Localization = settings.localization
    fun getOrderType(): OrderType = settings.orderType
    fun getOrderRow(): TableRow = settings.orderedRow

    suspend fun setLocalization(localization: Localization) {
        settings.localization = localization
        AppState.updateLocalization()
    }

    fun getPurchaseTime(): Instant {
        return settings.purchaseTime
    }

    suspend fun setPurchaseTime(purchaseTime: Instant) {
        settings.purchaseTime = purchaseTime
    }
}