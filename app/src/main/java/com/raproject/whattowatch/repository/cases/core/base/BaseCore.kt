package com.raproject.whattowatch.repository.cases.core.base

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.utils.ContentType
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.RequestManager
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseCore<T> : KoinComponent {

    protected val requestManager: RequestManager by inject()

    protected fun getGenresFun(localization: Localization):
        suspend (String, SQLiteDatabase) -> Deferred<String> {
        return when (localization) {
            Localization.English -> this::getGenresENAsync
            Localization.Russian -> this::getGenresRUAsync
        }
    }

    abstract val contentType: ContentType

    protected abstract suspend fun getContentCoreRequest(
        database: SQLiteDatabase,
        localization: Localization
    ): T

    private suspend fun getGenresENAsync(
        key: String,
        database: SQLiteDatabase
    ): Deferred<String> {
        return getGenresAsync(key, database, Localization.English)
    }

    private suspend fun getGenresRUAsync(
        key: String,
        database: SQLiteDatabase
    ): Deferred<String> {
        return getGenresAsync(key, database, Localization.Russian)
    }

    private suspend fun getGenresAsync(
        key: String,
        database: SQLiteDatabase,
        localization: Localization
    ):
        Deferred<String> = withContext(Dispatchers.Default) {
        async(Dispatchers.Default) {
            var genesTemp = ""
            val genres = with(requestManager) { database.getGenresForTitle(key, localization) }
            genres.moveToFirst()
            for (i in 1..19) {
                if (genres.getString(i) != null)
                    genesTemp += genres.getString(i)
            }
            return@async genesTemp
        }
    }

    protected suspend fun getPostersCursorAsync(
        key: String,
        database: SQLiteDatabase
    ):
        Deferred<Cursor> = withContext(Dispatchers.Default) {
        async {
            return@async with(requestManager) { database.getSmallPoster(key) }
        }
    }
}
