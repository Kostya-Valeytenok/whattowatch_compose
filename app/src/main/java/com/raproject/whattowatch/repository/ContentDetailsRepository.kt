package com.raproject.whattowatch.repository

class ContentDetailsRepository(private val requestManager: RequestManager) : Repository() {

    suspend fun addContentToFavorite(contentId:String) = runRequest{
        requestManager.postToFavorite(contentId)
    }

    suspend fun deleteContentFromFavorite(contentId:String) = runRequest{
        requestManager.deleteFromFavorite(contentId)
    }

    suspend fun getIsInFavoriteStatus(contentId: String) = runRequest {
        requestManager.getIsInFavoriteStatus(contentId)
    }
}