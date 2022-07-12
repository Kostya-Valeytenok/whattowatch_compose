package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase

object DatabaseBuilder {

    fun SQLiteDatabase.build(){
        BuildMainTableTask.run { this@build.runTask() }
        BuildGenresTableTask.run { this@build.runTask() }
        BuildPostersTableTask.run { this@build.runTask() }
        BuildMoviesTableTask.run { this@build.runTask() }
        BuildAnimeTableTask.run { this@build.runTask() }
        BuildTVShowsTableTask.run { this@build.runTask() }
        BuildCartoonsTableTask.run { this@build.runTask() }
    }
}