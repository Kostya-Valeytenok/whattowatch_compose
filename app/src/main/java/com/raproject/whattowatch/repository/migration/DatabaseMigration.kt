package com.raproject.whattowatch.repository.migration

import android.database.sqlite.SQLiteDatabase

sealed class DatabaseMigration(private val databaseVersion: Int) {

    fun SQLiteDatabase.execute(currentDatabaseVersion: Int) {
        println("Migrate from $currentDatabaseVersion to $databaseVersion")
        if (currentDatabaseVersion < databaseVersion) execute()
    }

    protected abstract fun SQLiteDatabase.execute()
}
