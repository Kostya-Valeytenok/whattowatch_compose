package com.raproject.whattowatch.repository.cases.core.base

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.asyncJob
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

abstract class BaseContentCaseCore : BaseCore<List<ContentItem>>(), KoinComponent {

    val contentRequest: suspend (Localization) ->
    suspend (SQLiteDatabase) -> List<ContentItem> = { locale ->

        val request: suspend (SQLiteDatabase) -> List<ContentItem> = { db ->
            getContentCoreRequest(db, locale)
        }

        request
    }

    override suspend fun getContentCoreRequest(
        database: SQLiteDatabase,
        localization: Localization
    ): List<ContentItem> {

        return with(requestManager) { database.getContentCards(contentType, localization) }
            .use { it.prepareContentItemList(localization, database) }
    }

    private suspend fun Cursor.prepareContentItemList(
        localization: Localization,
        database: SQLiteDatabase
    ): List<ContentItem> = withContext(Dispatchers.Default) {
        val items = mutableListOf<ContentItem>()
        val taskList = mutableListOf<Deferred<ContentItem>>()
        while (!isAfterLast) {
            val id = getString(0)
            val title = getString(2)
            val year = getString(3)
            taskList.add(
                database.createItemTask(
                    contentId = id,
                    title = title,
                    titleReleaseYear = year,
                    localization = localization
                )
            )
            moveToNext()
        }
        taskList.forEach { items.add(it.await()) }
        items
    }

    private suspend fun SQLiteDatabase.createItemTask(
        contentId: String,
        title: String,
        titleReleaseYear: String,
        localization: Localization,
    ): Deferred<ContentItem> = asyncJob {
        val genesTemp: String
        val posterData: Cursor
        val generisFun = getGenresFun(localization)
        val genresTask = generisFun(contentId, this)
        val porterTask = getPostersCursorAsync(contentId, this)
        genesTemp = genresTask.await()
        posterData = porterTask.await()
        createItem(contentId, title, titleReleaseYear, posterData, genesTemp)
    }

    private fun createItem(
        id: String,
        title: String,
        titleReleaseYear: String,
        posterData: Cursor,
        genesTemp: String
    ): ContentItem {
        val item: ContentItem by inject {
            parametersOf(
                id.toInt(),
                BitmapFactory.decodeByteArray(posterData.getBlob(0), 0, posterData.getBlob(0)!!.size), // ktlint-disable max-line-length
                title,
                titleReleaseYear,
                genesTemp
            )
        }
        return item
    }
}
