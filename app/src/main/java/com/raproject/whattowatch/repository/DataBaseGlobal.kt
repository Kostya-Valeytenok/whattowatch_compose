package com.raproject.whattowatch.repository

import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.raproject.whattowatch.repository.request.GetRequest

const val DB_VERSION = 119

fun SQLiteOpenHelper.completeForWritable(
    onFailed: (Throwable) -> Unit = {},
    action: SQLiteDatabase.() -> Unit,
) {
    runCatching { this.writableDatabase }
        .onSuccess { db -> db.use { action.invoke(it) } }
        .onFailure { onFailed.invoke(it) }
}

fun SQLiteOpenHelper.getWritableDB(): SQLiteDatabase {
    val dataBase: SQLiteDatabase
    try {
        dataBase = writableDatabase
    } catch (mSQLException: SQLException) {
        throw mSQLException
    }
    return dataBase
}

suspend fun <T> GetRequest<T>.run(scope:SQLiteDatabase) : Result<T> {
   return scope.runRequest()
}