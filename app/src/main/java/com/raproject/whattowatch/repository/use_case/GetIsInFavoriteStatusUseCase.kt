package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.ContentDetailsRepository
import kotlinx.coroutines.Deferred

class GetIsInFavoriteStatusUseCase(override val repository: ContentDetailsRepository) :
    InOutCase<String, Deferred<Result<Boolean>>, ContentDetailsRepository>() {
    override suspend fun invoke(value: String): Deferred<Result<Boolean>> {
        return  repository.getIsInFavoriteStatus(contentId = value)
    }
}