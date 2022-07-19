package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.repository.migration.DatabaseMigrationTo120Version
import com.raproject.whattowatch.repository.runMigration

object DatabaseBuilder {

    var isNeedRollUpMigrations = true

    fun SQLiteDatabase.build() {
        BuildMainTableTask.run { this@build.runTask() }
        BuildGenresTableTask.run { this@build.runTask() }
        BuildPostersTableTask.run { this@build.runTask() }
        BuildMoviesTableTask.run { this@build.runTask() }
        BuildAnimeTableTask.run { this@build.runTask() }
        BuildTVShowsTableTask.run { this@build.runTask() }
        BuildCartoonsTableTask.run { this@build.runTask() }
        BuildFavoriteTableTask.run { this@build.runTask() }
        if (isNeedRollUpMigrations) rollUpMigrations()
    }

    private fun SQLiteDatabase.rollUpMigrations() {
        runMigration(DatabaseMigrationTo120Version, 119)
    }
}