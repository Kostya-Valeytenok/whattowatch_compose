package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase

object BuildFavoriteTableTask : DatabaseBuildTask() {
    override fun SQLiteDatabase.buildTask() {
        execSQL("CREATE TABLE \"Wanttowatch\" (\"_Key\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY)")
    }

    override fun SQLiteDatabase.inputData() {}
}