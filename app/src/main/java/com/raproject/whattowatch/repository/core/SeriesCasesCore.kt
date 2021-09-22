package com.raproject.whattowatch.repository.core

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import com.raproject.whattowatch.models.ContentItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class SeriesCasesCore : KoinComponent {

    val seriesENCore = this::getSeriesCore

    private suspend fun getSeriesCore(database: SQLiteDatabase): List<ContentItem> {
        val cursor = database.rawQuery(
            "select _Key, Image, Title, Year, Rating1 from MainTableEN where _Key in (select * from Series) ORDER BY Rating1 DESC", // ktlint-disable max-line-length
            null
        )
        cursor.moveToFirst()
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        val items = mutableListOf<ContentItem>()
        while (!cursor.isAfterLast) {
            scope.launch {
                val genesTemp: String
                val entry: Cursor
                val genresTask = getGenresAsync(scope, cursor.getString(0), database)
                val entryTask = getEntriesCursorAsync(scope, cursor.getString(0), database)
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

    private fun getGenresAsync(scope: CoroutineScope, key: String, database: SQLiteDatabase):
        Deferred<String> =
        scope.async {
            var genesTemp = ""
            val genres = database.rawQuery("SELECT * FROM GenresKeysEN WHERE _Key = $key", null)
            genres.moveToFirst()
            for (i in 1..19) {
                if (genres.getString(i) != null)
                    genesTemp += genres.getString(i)
            }
            return@async genesTemp
        }

    private fun getEntriesCursorAsync(scope: CoroutineScope, key: String, database: SQLiteDatabase):
        Deferred<Cursor> =
        scope.async {
            val entriesCursor = database.rawQuery("SELECT * FROM Postors WHERE _Key = " + key, null)
            entriesCursor.moveToNext()
            return@async entriesCursor
        }
}
