package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.utils.DBTable

abstract class DatabaseBuildTask {

    protected val mainTable = DBTable.MainTable
    protected val genresTable = DBTable.GenresTable

   protected abstract fun SQLiteDatabase.buildTask()

   protected abstract fun SQLiteDatabase.inputData()

   fun SQLiteDatabase.runTask(){
       buildTask()
       inputData()
   }

    protected fun dbValue(value:Any): String {
        return "\"$value\""
    }
}