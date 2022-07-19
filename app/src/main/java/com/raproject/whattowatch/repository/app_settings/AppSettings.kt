package com.raproject.whattowatch.repository.app_settings

import android.content.Context
import com.raproject.whattowatch.utils.DBTable
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.OrderType

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
}