package com.raproject.whattowatch.repository.cases

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.models.GetContentModel
import com.raproject.whattowatch.repository.ContentViewRepository
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.Deferred

class GetCartoonsUseCase(override val repository: ContentViewRepository) : InOutCase<GetContentModel,Deferred<List<ContentItem>>, ContentViewRepository>() {

    override suspend fun invoke(value:GetContentModel): Deferred<List<ContentItem>> {
       return repository.getCartoonsContentItems(localization = value.localization, orderCommand = value.orderCommand)
    }
}