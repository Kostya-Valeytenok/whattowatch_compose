package com.raproject.whattowatch.utils

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

fun ContentDetailsStatus.onLoaded.convertToContentViewModel(): ContentViewModel {

    val contentItems = contentItems.toMutableMap()

    val yearAndDuration = mutableListOf<String>().apply {
        contentItems.remove(DataContentType.YEAR)?.let { add(it.toString()) }
        contentItems.remove(DataContentType.DURATION)?.let { add(it.toString()) }
    }

    val contentInfoViewList = mutableListOf<ContentInfoView>()
    val yearAndDurationText = yearAndDuration.joinToString()

    var posterURL: Deferred<*>? = null

    contentItems.forEach { (type, data) ->
        when (type) {
            DataContentType.POSTER -> posterURL = data as? Deferred<*>
            DataContentType.TITLE -> contentInfoViewList.add(ContentInfoView.Title(title = data.toString()))
            DataContentType.GENRES -> contentInfoViewList.add(
                ContentInfoView.Genres(
                    genres = (data as? List<*>)?.joinToString()
                        ?: data.toString()
                )
            )
            DataContentType.SPACE -> contentInfoViewList.add(
                ContentInfoView.Space(
                    spaceInDP = data.toString().toInt()
                )
            )
            DataContentType.YEARANDDURATION -> contentInfoViewList.add(
                ContentInfoView.YearPlusDuration(
                    text = if (yearAndDurationText.isNotBlank()) yearAndDurationText else data.toString()
                )
            )
            DataContentType.CAST -> {}
            DataContentType.DEVRATING -> {}
            DataContentType.KINOPOISKRATING -> {}
            DataContentType.DIRECTOR -> {}
            DataContentType.DESCRIPTION -> {}

            else -> {}
        }
    }

    return ContentViewModel(id = id, posterURITask = posterURL, contentItems = contentInfoViewList)
}