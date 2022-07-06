package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.models.GetContentModel
import com.raproject.whattowatch.repository.ContentViewRepository
import kotlinx.coroutines.Deferred

class GetTop100UseCase(override val repository: ContentViewRepository) : InOutCase<GetContentModel,Deferred<List<ContentItem>>, ContentViewRepository>() {

    override suspend fun invoke(value: GetContentModel): Deferred<List<ContentItem>> {
       return repository.getTop100ContentItems(localization = value.localization)
    }
}