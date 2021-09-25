package com.raproject.whattowatch.repository.core

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

abstract class BaseCaseCore : BaseCore(), KoinComponent {

    override suspend fun getContentCoreRequest(
        database: SQLiteDatabase,
        localization: Localization
    ): List<ContentItem> {
        val contentTableName = getContentTableName(localization)
        val cursor = database.rawQuery(
            "select _Key, Image, Title, Year, Rating1 from $contentTableName where _Key in (select * from ${contentType.tableName}) ORDER BY Rating1 DESC", // ktlint-disable max-line-length
            null
        )
        cursor.moveToFirst()
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        val items = mutableListOf<ContentItem>()
        while (!cursor.isAfterLast) {
            scope.launch {
                val genesTemp: String
                val entry: Cursor
                val generisFun = getGenresFun(localization)
                val genresTask = generisFun(scope, cursor.getString(0), database)
                val entryTask = getPostersCursorAsync(scope, cursor.getString(0), database)
                genesTemp = genresTask.await()
                entry = entryTask.await()
                items.add(createItem(cursor, entry, genesTemp))
            }.join()
            cursor.moveToNext()
        }
        return items
    }

    private fun createItem(cursor: Cursor, entry: Cursor?, genesTemp: String): ContentItem {
        val item: ContentItem by inject {
            parametersOf(
                cursor.getInt(0),
                BitmapFactory.decodeByteArray(entry?.getBlob(1), 0, entry?.getBlob(1)!!.size),
                cursor.getString(2),
                cursor.getString(3),
                genesTemp
            )
        }
        return item
    }
}
