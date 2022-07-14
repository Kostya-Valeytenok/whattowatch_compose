package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.models.ContentDetailsStatus
import com.raproject.whattowatch.models.GetContentDetailsModel
import com.raproject.whattowatch.repository.ContentDetailsRepository
import kotlinx.coroutines.Deferred

class GetContentDetailsUseCase(override val repository: ContentDetailsRepository) :
    InOutCase<GetContentDetailsModel, Deferred<ContentDetailsStatus>, ContentDetailsRepository>() {

    override suspend fun invoke(value: GetContentDetailsModel): Deferred<ContentDetailsStatus> {
        return repository.getContentInfoById(
            contentId = value.contentId,
            localization = value.localization
        )
    }
}