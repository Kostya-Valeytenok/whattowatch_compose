package com.raproject.whattowatch.repository

import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException
import java.lang.Error

class DataBase() : KoinComponent {

    private val helper: DatabaseHelper by inject()

    private fun getDataBase(): SQLiteDatabase {
        var dataBase: SQLiteDatabase
        try {
            helper.updateDataBase()
        } catch (mIOException: IOException) {
            throw Error("UnableToUpdateDatabase")
        }
        try {
            dataBase = helper.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }
        return dataBase
    }

    suspend fun <T> execute(action: suspend (database: SQLiteDatabase) -> T): T {
        val database = getDataBase()
        val data = action.invoke(database)
        database.close()
        helper.close()
        return data
    }
}
