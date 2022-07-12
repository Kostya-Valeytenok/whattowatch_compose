package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase

object BuildTVShowsTableTask : DatabaseBuildTask() {
    override fun SQLiteDatabase.buildTask() {
        execSQL("CREATE TABLE \"Series\" (\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY)")
    }

    override fun SQLiteDatabase.inputData() {}
}