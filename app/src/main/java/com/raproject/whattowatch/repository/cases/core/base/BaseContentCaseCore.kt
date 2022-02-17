package com.raproject.whattowatch.repository.cases.core.base

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.ContentType
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.asyncJob
import kotlinx.coroutines.*
import org.koin.core.KoinComponent

abstract class BaseContentCaseCore : BaseCore<List<ContentItem>>(), KoinComponent {

    val contentRequest: suspend (Localization, String) ->
    suspend (SQLiteDatabase) -> List<ContentItem> = { locale, orderCommand ->

        val request: suspend (SQLiteDatabase) -> List<ContentItem> = { db ->
            getContentCoreRequest(db, locale, orderCommand)
        }

        request
    }

    override suspend fun getContentCoreRequest(
        database: SQLiteDatabase,
        localization: Localization,
        orderCommand: String
    ): List<ContentItem> {

        return with(requestManager) {
            database.getContentCards(contentType, localization, orderCommand)
        }.use { it.prepareContentItemList(localization, database) }
    }

    private suspend fun Cursor.prepareContentItemList(
        localization: Localization,
        database: SQLiteDatabase
    ): List<ContentItem> = withContext(Dispatchers.Default) {
        val items = mutableListOf<ContentItem>()
        val taskList = createTaskList(database = database, localization = localization)
        taskList.forEach { items.add(it.await()) }
        items
    }

    private suspend fun Cursor.createTaskList(
        database: SQLiteDatabase,
        localization: Localization,
    ): MutableList<Deferred<ContentItem>> {
        val taskList = mutableListOf<Deferred<ContentItem>>()
        while (!isAfterLast) {
            val id = getString(0)
            val title = getString(2)
            val year = getString(3)
            val rating = when (contentType) {
                ContentType.Top100 -> getString(4)
                else -> null
            }
            taskList.add(
                database.createItemTaskAsync(
                    contentId = id,
                    title = title,
                    titleReleaseYear = year,
                    localization = localization,
                    rating = rating
                )
            )
            moveToNext()
        }
        return taskList
    }

    private suspend fun SQLiteDatabase.createItemTaskAsync(
        contentId: String,
        title: String,
        titleReleaseYear: String,
        localization: Localization,
        rating: String? = null
    ): Deferred<ContentItem> = asyncJob {
        val genesTemp: String
        val posterData: Cursor
        val generisFun = getGenresFun(localization)
        val genresTask = generisFun(contentId, this)
        val porterTask = getPostersCursorAsync(contentId, this)
        genesTemp = genresTask.await()
        posterData = porterTask.await()
        createItem(contentId, title, titleReleaseYear, posterData, genesTemp, rating)
    }

    private fun createItem(
        id: String,
        title: String,
        titleReleaseYear: String,
        posterData: Cursor,
        genesTemp: String,
        rating: String? = null
    ): ContentItem {
        return ContentItem(
            id.toInt(),
            BitmapFactory.decodeByteArray(posterData.getBlob(0), 0, posterData.getBlob(0)!!.size), // ktlint-disable max-line-length
            title,
            titleReleaseYear,
            genesTemp,
            rating
        )
    }
}
