package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.ContentViewRepository
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.Deferred

class GetFavoriteUseCase(override val repository: ContentViewRepository) :
    InOutCase<Localization, Deferred<Result<List<ContentItem>>>, ContentViewRepository>() {

    override suspend fun invoke(value: Localization): Deferred<Result<List<ContentItem>>> {
        return repository.getFavorieContentItems(value)
    }
}