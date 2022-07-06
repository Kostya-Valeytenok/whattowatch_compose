package com.raproject.whattowatch.repository

import com.raproject.whattowatch.utils.ContentType
import com.raproject.whattowatch.utils.DBTable
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.OrderType
import org.koin.core.component.KoinComponent

class ContentViewRepository(private val requestManager: RequestManager) : Repository(), KoinComponent {

    suspend fun getTop100ContentItems(localization: Localization) = runRequest {
        requestManager.getContentCardsByType(
            contentType = ContentType.Top100,
            localization = localization,
            orderCommand = OrderType.DescendingOrder.by(DBTable.MainTable.DevRating)
        )
    }

    suspend fun getAnimeContentItems(localization: Localization, orderCommand:String) = runRequest {
        requestManager.getContentCardsByType(
            contentType = ContentType.Anime,
            localization = localization,
            orderCommand = orderCommand
        )
    }

    suspend fun getMoviesContentItems(localization: Localization, orderCommand:String) = runRequest {
        requestManager.getContentCardsByType(
            contentType = ContentType.Movies,
            localization = localization,
            orderCommand = orderCommand
        )
    }

    suspend fun getCartoonsContentItems(localization: Localization, orderCommand:String) = runRequest {
        requestManager.getContentCardsByType(
            contentType = ContentType.Cartoons,
            localization = localization,
            orderCommand = orderCommand
        )
    }

    suspend fun getTVShowsContentItems(localization: Localization, orderCommand:String) = runRequest {
        requestManager.getContentCardsByType(
            contentType = ContentType.TVShows,
            localization = localization,
            orderCommand = orderCommand
        )
    }
}