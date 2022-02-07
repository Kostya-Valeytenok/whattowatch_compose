package com.raproject.whattowatch.ui.content_list

import androidx.lifecycle.viewModelScope
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.cases.MoviesCases
import com.raproject.whattowatch.repository.cases.SeriesCases
import com.raproject.whattowatch.ui.DrawerScreen
import com.raproject.whattowatch.utils.BaseViewModel
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class AppActivityViewModel : BaseViewModel(), KoinComponent {

    private val moviesCases: MoviesCases by inject()
    private val seriesCases: SeriesCases by inject()

    var content: MutableStateFlow<List<ContentItem>> = MutableStateFlow(mutableListOf())
    var loadingStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val screenTypeRX: MutableStateFlow<DrawerScreen> = MutableStateFlow(DrawerScreen.Movies)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            screenTypeRX.collect { type ->
                when (type) {
                    DrawerScreen.Anime -> {}
                    DrawerScreen.Cartoons -> {}
                    DrawerScreen.Movies -> getContent { moviesCases.getFilms(Localization.English) }
                    DrawerScreen.Serials -> getContent { seriesCases.getSeries(Localization.English) } // ktlint-disable max-line-length
                    DrawerScreen.Top100 -> {}
                    DrawerScreen.WantToWatch -> {}
                    DrawerScreen.Watched -> {}
                }
            }
        }
    }

    fun setScreenType(newType: DrawerScreen) {
        viewModelScope.launch { screenTypeRX.emit(newType) }
    }

    private suspend fun getContent(contentRequest: suspend () -> List<ContentItem>) {
        viewModelScope.launch(Dispatchers.Default) {
            loadingStatus.emit(true)
            content.emit(contentRequest.invoke())
            loadingStatus.emit(false)
        }
    }
}
