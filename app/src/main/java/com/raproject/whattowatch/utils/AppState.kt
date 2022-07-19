package com.raproject.whattowatch.utils

import com.raproject.whattowatch.repository.use_case.GetLocalizationUseCase
import com.raproject.whattowatch.repository.use_case.GetOrderRowUseCase
import com.raproject.whattowatch.repository.use_case.GetOrderTypeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AppState : KoinComponent {

    val settingsErrorCallback: (Throwable) -> Unit = { error ->
        println(error)
    }

    val bookmarkHasChange = MutableStateFlow<String>("0")

    private val getLocalizationUseCase: GetLocalizationUseCase by inject()
    private val getOrderRowUseCase: GetOrderRowUseCase by inject()
    private val getOrderTypeUseCase: GetOrderTypeUseCase by inject()

    private val localizationState = MutableStateFlow(getLocalizationUseCase.invoke())
    private val orderTypeState = MutableStateFlow(getOrderTypeUseCase.invoke())
    private val orderedRowState = MutableStateFlow(getOrderRowUseCase.invoke())

    val localization: StateFlow<Localization>
        get() = localizationState

    val orderType: StateFlow<OrderType>
        get() = orderTypeState

    val orderedRow: StateFlow<TableRow>
        get() = orderedRowState

    suspend fun updateLocalization() {
        localizationState.emit(getLocalizationUseCase.invoke())
    }

    suspend fun updateOrderType() {
        orderTypeState.emit(getOrderTypeUseCase.invoke())
    }

    suspend fun updateOrderRow() {
        orderedRowState.emit(getOrderRowUseCase.invoke())
    }
}
