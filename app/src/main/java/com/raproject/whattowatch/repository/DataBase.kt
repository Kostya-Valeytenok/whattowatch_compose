package com.raproject.whattowatch.repository

import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.repository.request.GetRequest
import com.raproject.whattowatch.repository.request.PostRequest
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataBase() : KoinComponent {

    private val helper: DatabaseHelper by inject()

    private fun getDataBase(): SQLiteDatabase {
        runCatching { helper.updateDataBase() }
        return helper.getWritableDB()
    }

    private val mutex = Mutex()

    suspend fun <T> execute(request: GetRequest<T>): T {
        return mutex.useDataBase { request.run { runRequest() } }
    }

    suspend fun <T> execute(request: PostRequest<T>): Result<Unit> {
        return mutex.useDataBase { request.run { runCatching { runRequest() } } }
    }

    private suspend fun <T> Mutex.useDataBase(request: suspend SQLiteDatabase.() -> T): T {
        return withLock {
            helper.use {
                getDataBase().use { database ->
                    request.invoke(database)
                }
            }
        }
    }
}
