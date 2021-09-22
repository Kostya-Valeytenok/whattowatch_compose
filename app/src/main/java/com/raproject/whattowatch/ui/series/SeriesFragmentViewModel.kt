package com.raproject.whattowatch.ui.series

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.series.SeriesCases
import com.raproject.whattowatch.utils.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class SeriesFragmentViewModel : BaseViewModel(), KoinComponent {

    private val seriesCases: SeriesCases by inject()

    var series: MutableState<List<ContentItem>> = mutableStateOf(mutableListOf())
    var seriesLoadingStatus: MutableState<Boolean> = mutableStateOf(false)

    fun getSeries() {
        viewModelScope.launch {
            seriesLoadingStatus.value = true
            series.value = seriesCases.getSeries()
            seriesLoadingStatus.value = false
        }
    }
}
