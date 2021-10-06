package com.raproject.whattowatch.repository.cases.core.base

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.ContentType
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

abstract class BaseCore() {

    protected fun getGenresFun(localization: Localization):
        (CoroutineScope, String, SQLiteDatabase) -> Deferred<String> {
        return when (localization) {
            Localization.English -> this::getGenresENAsync
            Localization.Russian -> this::getGenresRUAsync
        }
    }

    abstract val contentType: ContentType

    protected abstract suspend fun getContentCoreRequest(
        database: SQLiteDatabase,
        localization: Localization
    ): List<ContentItem>

    private fun getGenresENAsync(
        scope: CoroutineScope,
        key: String,
        database: SQLiteDatabase
    ): Deferred<String> {
        return getGenresAsync(scope, key, database, Localization.English)
    }

    private fun getGenresRUAsync(
        scope: CoroutineScope,
        key: String,
        database: SQLiteDatabase
    ): Deferred<String> {
        return getGenresAsync(scope, key, database, Localization.Russian)
    }

    private fun getGenresAsync(
        scope: CoroutineScope,
        key: String,
        database: SQLiteDatabase,
        localization: Localization
    ):
        Deferred<String> =
        scope.async {
            var genesTemp = ""
            val genresTableName = getGenresTableName(localization)
            val genres = database.rawQuery("SELECT * FROM $genresTableName WHERE _Key = $key", null)
            genres.moveToFirst()
            for (i in 1..19) {
                if (genres.getString(i) != null)
                    genesTemp += genres.getString(i)
            }
            return@async genesTemp
        }

    protected fun getContentTableName(localization: Localization): String {
        return when (localization) {
            Localization.English -> "MainTableEN"
            Localization.Russian -> "MainTable"
        }
    }

    private fun getGenresTableName(localization: Localization): String {
        return when (localization) {
            Localization.English -> "GenresKeysEN"
            Localization.Russian -> "GenresKeys"
        }
    }

    protected fun getPostersCursorAsync(
        scope: CoroutineScope,
        key: String,
        database: SQLiteDatabase
    ):
        Deferred<Cursor> =
        scope.async {
            val entriesCursor = database.rawQuery("SELECT * FROM Postors WHERE _Key = $key", null)
            entriesCursor.moveToNext()
            return@async entriesCursor
        }
}
