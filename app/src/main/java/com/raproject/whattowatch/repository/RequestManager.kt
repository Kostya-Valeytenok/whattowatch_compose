package com.raproject.whattowatch.repository

import android.os.Bundle
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.request.GetContentCardsByType
import com.raproject.whattowatch.repository.request.GetRequest
import com.raproject.whattowatch.repository.request.PostRequest
import com.raproject.whattowatch.utils.ContentType
import com.raproject.whattowatch.utils.Localization
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class RequestManager : KoinComponent {

    private val dataBase: DataBase by inject()

    suspend fun getContentCardsByType(
        contentType: ContentType,
        localization: Localization,
        orderCommand: String
    ): List<ContentItem> =
        createGetRequest<GetContentCardsByType>(
            GetContentCardsByType.createParams(
                contentType = contentType,
                localization = localization,
                orderCommand = orderCommand
            )
        ).execute()

    private suspend fun <T> GetRequest<T>.execute(): T {
        return dataBase.execute(this)
    }

    private suspend fun <T> PostRequest<T>.execute(): Result<Unit> {
        return dataBase.execute(this)
    }

    private inline fun <reified T : PostRequest<V>, V> createPostRequest(data: V): T {
        val request: T by inject { parametersOf(data) }
        return request
    }

    private inline fun <reified T : GetRequest<*>> createGetRequest(data: Bundle? = null): T {
        val request: T by inject { parametersOf(data) }
        return request
    }
}
