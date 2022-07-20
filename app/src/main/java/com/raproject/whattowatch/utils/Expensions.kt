package com.raproject.whattowatch.utils

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.QueryProductDetailsParams
import com.raproject.whattowatch.models.ContentDetailsStatus
import com.raproject.whattowatch.models.ContentViewModel
import com.raproject.whattowatch.models.DataContentType
import com.raproject.whattowatch.ui.ContentInfoView
import kotlinx.coroutines.Deferred

inline fun <T> Collection<T>.forEachIndexedWithLastMarker(
    action: (index: Int, T, isLast: Boolean) -> Unit
) {
    forEachIndexed { index, t ->
        action.invoke(index, t, (size - 1 == index))
    }
}

fun List<String>.createQueryProductDetailsParamsForSubscription() = buildList {
    this@createQueryProductDetailsParamsForSubscription.forEach {
        add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        )
    }

}

fun ContentDetailsStatus.onLoaded.convertToContentViewModel(): ContentViewModel {

    val contentItems = contentItems.toMutableMap()
    var devRating: String?
    var kinopoinskRating: String?

    val yearAndDuration = mutableListOf<String>().apply {
        contentItems.remove(DataContentType.YEAR)?.let { add(it.toString()) }
        contentItems.remove(DataContentType.DURATION)?.let { add(it.toString()) }
        contentItems.remove(DataContentType.KINOPOISKRATING)
            .let { kinopoinskRating = it.toString() }
        contentItems.remove(DataContentType.DEVRATING).let { devRating = it.toString() }
    }

    val contentInfoViewList = mutableListOf<ContentInfoView>()
    val yearAndDurationText = yearAndDuration.joinToString()

    var posterURL: Deferred<*>? = null

    contentItems.forEach { (type, data) ->
        println(type)
        when (type) {
            is DataContentType.POSTER -> posterURL = data as? Deferred<*>
            is DataContentType.TITLE -> contentInfoViewList.add(ContentInfoView.Title(title = data.toString()))
            is DataContentType.GENRES -> contentInfoViewList.add(
                ContentInfoView.Genres(
                    genres = (data as? List<*>)?.joinToString()
                        ?: data.toString()
                )
            )
            is DataContentType.SPACE -> contentInfoViewList.add(
                ContentInfoView.Space(
                    spaceInDP = data.toString().toInt()
                )
            )
            is DataContentType.YEARANDDURATION -> contentInfoViewList.add(
                ContentInfoView.YearPlusDuration(
                    text = if (yearAndDurationText.isNotBlank()) yearAndDurationText else data.toString()
                )
            )
            is DataContentType.CAST -> contentInfoViewList.add(
                ContentInfoView.Cast(
                    castText = (data as? List<*>)?.joinToString() ?: data.toString()
                )
            )
            is DataContentType.DEVRATING -> {}
            is DataContentType.KINOPOISKRATING -> {}
            is DataContentType.DIRECTOR -> contentInfoViewList.add(
                ContentInfoView.Director(
                    director = (data as? List<*>)?.joinToString() ?: data.toString()
                )
            )
            is DataContentType.DESCRIPTION -> contentInfoViewList.add(
                ContentInfoView.Description(
                    data.toString()
                )
            )
            is DataContentType.RATINGVIEW -> {

                if (devRating == null && kinopoinskRating == null) {

                    if (data is List<*>) {
                        kinopoinskRating = runCatching { data[0].toString() }.getOrNull()
                        devRating = runCatching { data[1].toString() }.getOrNull()
                    } else devRating = data.toString()
                }

                contentInfoViewList.add(
                    ContentInfoView.Rating(
                        kinopoiskRating = kinopoinskRating,
                        devRating = devRating
                    )
                )
            }
            else -> {}
        }
    }

    return ContentViewModel(id = id, posterURITask = posterURL, contentItems = contentInfoViewList)
}