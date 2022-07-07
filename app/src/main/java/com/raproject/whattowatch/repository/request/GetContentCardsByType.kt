package com.raproject.whattowatch.repository.request

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.os.Bundle
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.run
import com.raproject.whattowatch.utils.ContentType
import com.raproject.whattowatch.utils.DBTable
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.asyncJob
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class GetContentCardsByType(private val params: Bundle) : GetRequest<List<ContentItem>>(), KoinComponent {


    companion object {
        private const val LOCALIZATION_KEY = "LOCALIZATION_KEY"
        private const val CONTENT_TYPE = "CONTENT_TYPE"
        private const val ORDER_COMMAND = "ORDER_TYPE"

        fun createParams(
            contentType: ContentType,
            localization: Localization,
            orderCommand: String
        ): Bundle = Bundle().apply {
            putSerializable(CONTENT_TYPE, contentType)
            putString(ORDER_COMMAND, orderCommand)
            putSerializable(LOCALIZATION_KEY, localization)
        }
    }

    private val contentType: ContentType by lazy { params.getSerializable(CONTENT_TYPE) as ContentType }
    private val localization: Localization by lazy { params.getSerializable(LOCALIZATION_KEY) as Localization }
    private val orderCommand: String by lazy { params.getString(ORDER_COMMAND, "") }

    override suspend fun SQLiteDatabase.runRequest(): Result<List<ContentItem>> = runCatching {
        val contentTable = mainTable.tableName(localization)
        val key = DBTable.MainTable.key

        val cardContentFields = contentType.getCardContentFields()

        safeGetRequest(sqlCommand = "select $cardContentFields from $contentTable where $key in (${getSource()})$orderCommand"){
            prepareContentItemList(this@runRequest)
        }.getOrThrow()
    }

    private fun ContentType.getCardContentFields(): String {
        return (if (this == ContentType.Top100) mainTable.cardContentFieldsWithRating
        else mainTable.cardContentFields).joinToString()
    }

    private suspend fun SQLiteDatabase.getSource():String{
        var source = command("select * from ${contentType.tableName}")

        if (contentType == ContentType.Top100){
            getTop100ByDevRating().onSuccess { source = it }
        }
        return source
    }

    private suspend fun SQLiteDatabase.getTop100ByDevRating(): Result<String> {
        val getTop100Request:GetTop100 by inject {
            parametersOf(GetTop100.createParams(
                localization = localization,
                orderCommand = orderCommand
            ))
        }
        return with(getTop100Request){ runRequest() }
    }

    private suspend fun Cursor.prepareContentItemList(database: SQLiteDatabase): List<ContentItem> = withContext(Dispatchers.Default) {
        val items = mutableListOf<ContentItem>()
        val taskList = createTaskList(database = database)
        taskList.forEach { items.add(it.await()) }
        items
    }

    private suspend fun Cursor.createTaskList(database: SQLiteDatabase): MutableList<Deferred<ContentItem>> {
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
                    rating = rating
                )
            )
            toNext()
        }
        return taskList
    }

    private suspend fun SQLiteDatabase.createItemTaskAsync(
        contentId: String,
        title: String,
        titleReleaseYear: String,
        rating: String? = null
    ): Deferred<ContentItem> = asyncJob {
        val genes: String
        val poster: Bitmap?
        val genresTask = asyncJob { getGenresById(contentId) }
        val porterTask = asyncJob { posterById(contentId) }
        genes = genresTask.await()
        poster = porterTask.await()
        createItem(contentId, title, titleReleaseYear, poster, genes, rating)
    }

    private fun createItem(
        id: String,
        title: String,
        titleReleaseYear: String,
        poster: Bitmap?,
        genes: String,
        rating: String? = null
    ): ContentItem {
        return ContentItem(
            id.toInt(),
            poster,
            title,
            titleReleaseYear,
            genes,
            rating
        )
    }

    private suspend fun SQLiteDatabase.getGenresById(contentId:String): String {
        val getGenresByIdRequest:GetGenresById by inject {
            parametersOf(GetGenresById.createParams(
                contentId= contentId,
                localization = localization
            ))
        }
        return getGenresByIdRequest.run(scope = this).getOrNull()?:""
    }

    private suspend fun SQLiteDatabase.posterById(contentId:String): Bitmap? {
        val getPosterByIdRequest:GetSmallPosterById by inject {
            parametersOf(GetSmallPosterById.createParams(contentId= contentId))
        }
        return getPosterByIdRequest.run(scope = this).getOrNull()
    }

}