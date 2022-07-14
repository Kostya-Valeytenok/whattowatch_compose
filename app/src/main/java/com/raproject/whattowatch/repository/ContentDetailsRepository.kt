package com.raproject.whattowatch.repository

import com.raproject.whattowatch.models.ContentDetailsStatus
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.RequestInitError

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
        requestManager.postToFavorite(contentId)
    }

    suspend fun deleteContentFromFavorite(contentId: String) = runRequest {
        requestManager.deleteFromFavorite(contentId)
    }

    suspend fun getIsInFavoriteStatus(contentId: String) = runRequest {
        requestManager.getIsInFavoriteStatus(contentId)
    }
}