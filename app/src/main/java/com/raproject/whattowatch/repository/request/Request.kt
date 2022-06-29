package com.raproject.whattowatch.repository.request

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.utils.DBTable
import kotlinx.coroutines.yield
import org.intellij.lang.annotations.Language

open class Request {

    protected val mainTable = DBTable.MainTable
    protected val genresTable = DBTable.GenresTable
    protected val portersTable = DBTable.Posters

    protected suspend fun <T> SQLiteDatabase.safeGetRequest(@Language("SQL") sqlCommand: String, action: suspend Cursor.() -> T): T {
        return rawQuery(sqlCommand, null).use {
            it.moveToFirst()
            action.invoke(it)
        }
    }

    protected suspend fun Cursor.toNext() {
        yield()
        moveToNext()
    }
}
