package com.raproject.whattowatch.test_database

import android.database.sqlite.SQLiteDatabase

object DatabaseBuilder {

    fun SQLiteDatabase.build(){
        BuildMainTableTask.run { this@build.runTask() }
        BuildGenresTableTask.run { this@build.runTask() }
        BuildPostersTableTask.run { this@build.runTask() }
        BuildMoviesTableTask.run { this@build.runTask() }
    }
}