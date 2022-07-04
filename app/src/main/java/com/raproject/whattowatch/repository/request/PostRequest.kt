package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase

abstract class PostRequest<T>(protected val data: T) : Request() {

    abstract suspend fun SQLiteDatabase.runRequest()
}
