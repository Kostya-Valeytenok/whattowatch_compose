package com.raproject.whattowatch.repository

class ContentDetailsRepository(private val requestManager: RequestManager) : Repository() {

    suspend fun addContentToFavorite(contentId:String) = runRequest{
        requestManager.postToFavorite(contentId)
    }

    suspend fun getIsInFavoriteStatus(contentId: String) = runRequest {
        requestManager.getIsInFavoriteStatus(contentId)
    }
}