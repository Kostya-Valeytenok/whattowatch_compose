package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase

object BuildCartoonsTableTask : DatabaseBuildTask() {
    override fun SQLiteDatabase.buildTask() {
        execSQL("CREATE TABLE \"Cartoons\" (\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY)")
    }

    override fun SQLiteDatabase.inputData() {}
}