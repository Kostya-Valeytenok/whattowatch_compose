package com.raproject.whattowatch.ui.content_list

import androidx.lifecycle.viewModelScope
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.use_case.SetLocalizationUseCase
import com.raproject.whattowatch.ui.DrawerScreen
import com.raproject.whattowatch.utils.BaseViewModel
import com.raproject.whattowatch.utils.ContentProvider
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class AppActivityViewModel : BaseViewModel() {

    private val contentProvider: ContentProvider by inject()
    private val setLocalizationUseCase: SetLocalizationUseCase by inject()

    var content: MutableStateFlow<List<ContentItem>> = MutableStateFlow(mutableListOf())
    var loadingStatus: StateFlow<Boolean> = contentProvider.loadingStatus
    val screenTypeState: MutableStateFlow<DrawerScreen> = MutableStateFlow(DrawerScreen.Movies)

    init {

        viewModelScope.launch(Dispatchers.Default) {
            contentProvider.getScreenContent(screenTypeState).distinctUntilChanged().collect {
                content.emit(it)
            }
        }
    }

    fun setScreenType(newType: DrawerScreen) {
        viewModelScope.launch { screenTypeState.emit(newType) }
    }

    suspend fun setLocalization(localization: Localization) {
        setLocalizationUseCase.invoke(localization).onFailure { println(it) }
    }
}
