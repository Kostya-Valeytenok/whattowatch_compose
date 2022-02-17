package com.raproject.whattowatch.utils

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.cases.*
import com.raproject.whattowatch.ui.DrawerScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import com.raproject.whattowatch.utils.Settings.localization
import com.raproject.whattowatch.utils.Settings.orderType
import com.raproject.whattowatch.utils.Settings.orderedRow

class ContentProvider : KoinComponent {

    var loadingStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val moviesCases: MoviesCases by inject()
    private val seriesCases: SeriesCases by inject()
    private val cartoonsCases: CartoonsCases by inject()
    private val animeCases: AnimeCases by inject()
    private val top100Cases: Top100Cases by inject()

    suspend fun getScreenContent(
        screen: MutableStateFlow<DrawerScreen>
    ): Flow<List<ContentItem>> = withContext(Dispatchers.Default) {
        return@withContext combine(screen, localization, orderType, orderedRow) {
            type, localization, orderType, orderedRow, ->

            return@combine when (type) {
                DrawerScreen.Anime ->
                    getContent { animeCases.getAnime(localization, orderType.by(orderedRow)) }
                DrawerScreen.Cartoons ->
                    getContent { cartoonsCases.getCartoons(localization, orderType.by(orderedRow)) }
                DrawerScreen.Movies ->
                    getContent { moviesCases.getFilms(localization, orderType.by(orderedRow)) }
                DrawerScreen.Serials ->
                    getContent { seriesCases.getSeries(localization, orderType.by(orderedRow)) }
                DrawerScreen.Top100 ->
                    getContent { top100Cases.getTop100(localization, OrderType.DescendingOrder.by(DBTable.MainTable.DevRating)) }
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
