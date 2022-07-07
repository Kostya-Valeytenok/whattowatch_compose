package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.models.GetContentModel
import com.raproject.whattowatch.repository.ContentViewRepository
import kotlinx.coroutines.Deferred

class GetTVShowsUseCase(override val repository: ContentViewRepository) : InOutCase<GetContentModel,Deferred<Result<List<ContentItem>>>, ContentViewRepository>() {

    override suspend fun invoke(value:GetContentModel): Deferred<Result<List<ContentItem>>> {
       return repository.getTVShowsContentItems(localization = value.localization, orderCommand = value.orderCommand)
    }
}