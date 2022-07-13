package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.ContentDetailsRepository

class AddContentToFavoriteUseCase(override val repository: ContentDetailsRepository) :
    InOutCase<String, Result<Unit>, ContentDetailsRepository>() {

    override suspend fun invoke(value: String): Result<Unit> {
       return repository.addContentToFavorite(contentId = value).await()
    }
}