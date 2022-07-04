package com.raproject.whattowatch.utils

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.models.GetContentModel
import com.raproject.whattowatch.repository.cases.*
import com.raproject.whattowatch.ui.DrawerScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.raproject.whattowatch.utils.Settings.localization
import com.raproject.whattowatch.utils.Settings.orderType
import com.raproject.whattowatch.utils.Settings.orderedRow

class ContentProvider : KoinComponent {

    var loadingStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val getMoviesUseCase: GetMoviesUseCase by inject()
    private val getTVShowsUseCase: GetTVShowsUseCase by inject()
    private val getCartoonsUseCase: GetCartoonsUseCase by inject()
    private val getAmineUseCase : GetAmineUseCase by inject()
    private val getTop100UseCase: GetTop100UseCase by inject()

    suspend fun getScreenContent(
        screen: MutableStateFlow<DrawerScreen>
    ): Flow<List<ContentItem>> = withContext(Dispatchers.Default) {
        return@withContext combine(screen, localization, orderType, orderedRow) {
            type, localization, orderType, orderedRow, ->

            val getContentModel = GetContentModel(localization, orderType.by(orderedRow))
            return@combine when (type) {
                DrawerScreen.Anime -> getContent { getAmineUseCase.invoke(getContentModel).await() }
                DrawerScreen.Cartoons -> getContent { getCartoonsUseCase.invoke(getContentModel).await() }
                DrawerScreen.Movies -> getContent { getMoviesUseCase.invoke(getContentModel).await() }
                DrawerScreen.Serials -> getContent { getTVShowsUseCase.invoke(getContentModel).await() }
                DrawerScreen.Top100 -> getContent { getTop100UseCase.invoke(getContentModel).await() }
                DrawerScreen.WantToWatch -> listOf()
                DrawerScreen.Watched -> listOf()
            }
        }
    }

    private suspend fun getContent(contentRequest: suspend () -> List<ContentItem>):
        List<ContentItem> {
        loadingStatus.emit(true)
        val content = contentRequest.invoke()
        loadingStatus.emit(false)
        return content
    }
}
