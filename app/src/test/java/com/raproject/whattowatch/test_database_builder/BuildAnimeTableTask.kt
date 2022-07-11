package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase

object BuildAnimeTableTask : DatabaseBuildTask() {
    override fun SQLiteDatabase.buildTask() {
        execSQL("CREATE TABLE \"Anime\" (\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY)")
    }

    override fun SQLiteDatabase.inputData() {}
}