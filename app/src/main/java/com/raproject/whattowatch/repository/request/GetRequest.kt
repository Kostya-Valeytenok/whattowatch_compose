package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle

abstract class GetRequest<T>(requestParams: Bundle = Bundle()) : Request() {

    abstract suspend fun SQLiteDatabase.runRequest(): Result<T>
}
