package com.raproject.whattowatch.repository

import com.raproject.whattowatch.models.ContentDetailsStatus
import com.raproject.whattowatch.utils.*

class ContentDetailsRepository(private val requestManager: RequestManager) : Repository() {

    suspend fun getContentInfoById(contentId: String, localization: Localization) = runRequest {

        var result: ContentDetailsStatus =
            ContentDetailsStatus.OnFailed(throwable = RequestInitError())

        val requestResult = requestManager.getContentInfoById(contentId, localization)

        requestResult
            .onSuccess { result = it }
            .onFailure { result = ContentDetailsStatus.OnFailed(throwable = it) }

        result
    }

    suspend fun addContentToFavorite(contentId: String) = runRequest {
        val result = requestManager.postToFavorite(contentId)
        runCatching { AppState.bookmarkHasChange.emit(contentId + ADD_TO_FAVORITE) }
            .onFailure { AppState.settingsErrorCallback.invoke(it) }
        result
    }

    suspend fun deleteContentFromFavorite(contentId: String) = runRequest {
        val result = requestManager.deleteFromFavorite(contentId)
        runCatching { AppState.bookmarkHasChange.emit(contentId + REMOVE_FROM_FAVORITE) }
            .onFailure { AppState.settingsErrorCallback.invoke(it) }
        result
    }

    suspend fun getIsInFavoriteStatus(contentId: String) = runRequest {
        requestManager.getIsInFavoriteStatus(contentId)
    }
}