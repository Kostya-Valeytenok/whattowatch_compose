package com.raproject.whattowatch.repository.app_settings

import android.content.Context
import com.raproject.whattowatch.utils.DBTable
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.OrderType
import java.time.Instant

class AppSettings(context: Context) : AbstractPreferences("AppSettings", context = context) {

    var localization by PreferencesDelegateWithSerializer(
        "localization",
        Localization.English,
        Serializer.Localization
    )
    var orderType by PreferencesDelegateWithSerializer(
        "orderType",
        OrderType.Default,
        Serializer.OrderType
    )
    var orderedRow by PreferencesDelegateWithSerializer(
        "orderedRow",
        DBTable.MainTable.DevRating,
        Serializer.OrderedRow
    )

    var purchaseTime by PreferencesDelegateWithSerializer(
        "orderedRow",
        Instant.ofEpochSecond(0L),
        Serializer.Instant
    )
}