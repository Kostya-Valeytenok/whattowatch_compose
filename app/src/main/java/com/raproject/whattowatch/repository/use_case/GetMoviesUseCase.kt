package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.models.GetContentModel
import com.raproject.whattowatch.repository.ContentViewRepository
import kotlinx.coroutines.Deferred

class GetMoviesUseCase(override val repository: ContentViewRepository) : InOutCase<GetContentModel,Deferred<List<ContentItem>>, ContentViewRepository>() {

    override suspend fun invoke(value:GetContentModel): Deferred<List<ContentItem>> {
       return repository.getMoviesContentItems(localization = value.localization, orderCommand = value.orderCommand)
    }
}