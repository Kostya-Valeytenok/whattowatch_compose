package com.raproject.whattowatch.ui.content_list

import androidx.lifecycle.viewModelScope
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.ui.DrawerScreen
import com.raproject.whattowatch.utils.BaseViewModel
import com.raproject.whattowatch.utils.ContentProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class AppActivityViewModel : BaseViewModel(), KoinComponent {

    private val contentProvider: ContentProvider by inject()

    var content: MutableStateFlow<List<ContentItem>> = MutableStateFlow(mutableListOf())
    var loadingStatus: StateFlow<Boolean> = contentProvider.loadingStatus
    val screenTypeRX: MutableStateFlow<DrawerScreen> = MutableStateFlow(DrawerScreen.Movies)

    init {

        viewModelScope.launch(Dispatchers.Default) {
            contentProvider.getScreenContent(screenTypeRX).collect {
                content.emit(it)
            }
        }
    }

    fun setScreenType(newType: DrawerScreen) {
        viewModelScope.launch { screenTypeRX.emit(newType) }
    }
}
