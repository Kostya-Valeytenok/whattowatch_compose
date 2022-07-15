package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import com.raproject.whattowatch.models.ContentDetailsStatus
import com.raproject.whattowatch.models.DataContentType
import com.raproject.whattowatch.repository.RemoteRepository
import com.raproject.whattowatch.repository.run
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class GetContentInfoById(private val params: Bundle) : GetRequest<ContentDetailsStatus>(),
    KoinComponent {

    companion object {
        private const val LOCALIZATION_KEY = "LOCALIZATION_KEY"
        private const val CONTENT_ID = "CONTENT_ID"

        fun createParams(contentId: String, localization: Localization): Bundle = Bundle().apply {
            putString(CONTENT_ID, contentId)
            putSerializable(LOCALIZATION_KEY, localization)
        }
    }

    private val contentId: String = params.getString(CONTENT_ID, "")

    private val getGenresRequest: GetGenresById by inject { parametersOf(params) }

    override suspend fun SQLiteDatabase.runRequest(): Result<ContentDetailsStatus> =
        withContext(Dispatchers.Default) {
            runCatching {

                val localization: Localization =
                    params.getSerializable(LOCALIZATION_KEY) as Localization
                val getGenresTask =
                    async(Dispatchers.Default) { getGenresRequest.run(this@runRequest) }

                safeGetRequest("select * from ${mainTable.tableName(localization)} where ${mainTable.key} = $contentId") {
                    ContentDetailsStatus.onLoaded(
                        id = contentId,
                        contentItems = buildMap {
                            put(
                                DataContentType.POSTER,
                                RemoteRepository.getPosterURLById(getString(1))
                            )
                            put(DataContentType.TITLE, getString(2))
                            put(DataContentType.SPACE, 4)
                            put(DataContentType.YEARANDDURATION, "")
                            put(DataContentType.SPACE, 8)
                            put(DataContentType.YEAR, getString(3))
                            put(DataContentType.DURATION, getString(5))
                            getGenresTask.await().getOrNull()
                                ?.let { put(DataContentType.GENRES, it) }
                            put(DataContentType.COUNTRY, getString(4))
                            put(DataContentType.DEVRATING, getString(6))
                            put(DataContentType.KINOPOISKRATING, getString(7))
                            put(DataContentType.DIRECTOR, getString(8))
                            put(DataContentType.CAST, getString(9))
                            put(DataContentType.DESCRIPTION, getString(10))
                        }
                    )
                }.getOrThrow()
            }
        }
}