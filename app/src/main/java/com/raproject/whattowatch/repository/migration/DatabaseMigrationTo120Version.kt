package com.raproject.whattowatch.repository.migration

import android.database.sqlite.SQLiteDatabase

object DatabaseMigrationTo120Version : DatabaseMigration(databaseVersion = 120) {

    override fun SQLiteDatabase.execute() {
        renameFavoritesTableWithKeyRow()
    }

    private fun SQLiteDatabase.renameFavoritesTableWithKeyRow() {
        execSQL("ALTER TABLE Wanttowatch RENAME TO Favorite")
        execSQL("ALTER TABLE Favorite RENAME COLUMN _Key To contentId")
    }

}