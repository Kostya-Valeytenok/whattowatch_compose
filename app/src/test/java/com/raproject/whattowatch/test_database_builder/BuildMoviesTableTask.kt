package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase

object BuildMoviesTableTask : DatabaseBuildTask() {
    override fun SQLiteDatabase.buildTask() {
        execSQL("CREATE TABLE \"Films\" (\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY)")
    }

    override fun SQLiteDatabase.inputData() {
        execSQL("insert into Films values (1)")
        execSQL("insert into Films values (2)")
        execSQL("insert into Films values (3)")
    }
}