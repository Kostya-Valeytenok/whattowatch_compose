package com.raproject.whattowatch.repository.cases

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.models.GetContentModel
import com.raproject.whattowatch.repository.ContentViewRepository
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.Deferred

class GetAmineUseCase(override val repository: ContentViewRepository) : InOutCase<GetContentModel,Deferred<List<ContentItem>>, ContentViewRepository>() {

    override suspend fun invoke(value:GetContentModel): Deferred<List<ContentItem>> {
       return repository.getAnimeContentItems(localization = value.localization, orderCommand = value.orderCommand)
    }
}