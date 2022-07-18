package com.raproject.whattowatch.utils

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.models.GetContentModel
import com.raproject.whattowatch.repository.use_case.*
import com.raproject.whattowatch.ui.DrawerScreen
import com.raproject.whattowatch.utils.AppState.bookmarkHasChange
import com.raproject.whattowatch.utils.AppState.localization
import com.raproject.whattowatch.utils.AppState.orderType
import com.raproject.whattowatch.utils.AppState.orderedRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ContentProvider : KoinComponent {

    var loadingStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorFlow = MutableSharedFlow<Throwable>(extraBufferCapacity = 1)

    private val getMoviesUseCase: GetMoviesUseCase by inject()
    private val getTVShowsUseCase: GetTVShowsUseCase by inject()
    private val getCartoonsUseCase: GetCartoonsUseCase by inject()
    private val getAmineUseCase : GetAmineUseCase by inject()
    private val getTop100UseCase: GetTop100UseCase by inject()
    private val getFavoriteUseCase: GetFavoriteUseCase by inject()

    private var contentItemsCache = listOf<ContentItem>()
    private var screenCache: DrawerScreen? = null
    private var bookMarkIdCache = bookmarkHasChange.value

    suspend fun getScreenContent(
        screen: MutableStateFlow<DrawerScreen>
    ): Flow<List<ContentItem>> = withContext(Dispatchers.Default) {
        return@withContext combine(
            screen,
            localization,
            orderType,
            orderedRow,
            bookmarkHasChange
        ) { type, localization, orderType, orderedRow, id ->

            if (type != DrawerScreen.Favorite && bookMarkIdCache != id) return@combine contentItemsCache

            bookMarkIdCache = id

            val getContentModel = GetContentModel(localization, orderType.by(orderedRow))
            when (type) {
                DrawerScreen.Anime -> getContent { getAmineUseCase.invoke(getContentModel).await() }
                DrawerScreen.Cartoons -> getContent {
                    getCartoonsUseCase.invoke(getContentModel).await()
                }
                DrawerScreen.Movies -> getContent {
                    getMoviesUseCase.invoke(getContentModel).await()
                }
                DrawerScreen.Serials -> getContent {
                    getTVShowsUseCase.invoke(getContentModel).await()
                }
                DrawerScreen.Top100 -> getContent {
                    getTop100UseCase.invoke(getContentModel).await()
                }
                DrawerScreen.Favorite -> getContent {
                    getFavoriteUseCase.invoke(localization).await()
                }
            }.onSuccess {
                contentItemsCache = it
                screenCache = type
            }.onFailure {
                errorFlow.emit(it)
                if(screenCache != type) return@combine listOf()
            }

            contentItemsCache
        }
    }

    private suspend fun getContent(contentRequest: suspend () -> Result<List<ContentItem>>):
        Result<List<ContentItem>> {
        loadingStatus.emit(true)
        val content = contentRequest.invoke()
        loadingStatus.emit(false)
        return content
    }
}
