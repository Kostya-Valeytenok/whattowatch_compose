package com.raproject.whattowatch.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object Settings {

    private val localizationState = MutableStateFlow(Localization.English)
    private val orderTypeState = MutableStateFlow(OrderType.DescendingOrder)
    private val orderedRowState = MutableStateFlow(DBTable.MainTable.DevRating)

    val localization: StateFlow<Localization>
        get() = localizationState

    val orderType: StateFlow<OrderType>
        get() = orderTypeState

    val orderedRow: StateFlow<TableRow>
        get() = orderedRowState

    suspend fun setLocale(locale: Localization) {
        localizationState.emit(locale)
    }
}
