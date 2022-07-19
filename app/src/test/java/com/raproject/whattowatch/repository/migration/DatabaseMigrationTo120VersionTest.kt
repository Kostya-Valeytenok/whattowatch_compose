package com.raproject.whattowatch.repository.migration

import com.raproject.whattowatch.ShadowDatabase
import com.raproject.whattowatch.contentColumns
import com.raproject.whattowatch.repository.runMigration
import com.raproject.whattowatch.test_database_builder.DatabaseBuilder
import com.raproject.whattowatch.utils.DBTable
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
internal class DatabaseMigrationTo120VersionTest {

    init {
        DatabaseBuilder.isNeedRollUpMigrations = false
    }

    val shadowDatabase = ShadowDatabase()

    @Test
    fun execute() {
        runCatching {
            shadowDatabase.databaseHelper.writableDatabase.rawQuery(
                "select * from Wanttowatch",
                null
            ).use {
                assertEquals("_Key", it.columnNames.joinToString())
            }
        }
            .onFailure { assertTrue(false) }
            .onSuccess { assertTrue(true) }

        shadowDatabase.databaseHelper.writableDatabase.use {
            it.runMigration(
                DatabaseMigrationTo120Version,
                119
            )
        }

        runCatching {
            shadowDatabase.databaseHelper.writableDatabase.rawQuery(
                "select * from Wanttowatch",
                null
            ).use {
                assertEquals("_Key", it.columnNames.joinToString())
            }
        }
            .onFailure { assertTrue(true) }
            .onSuccess { assertTrue(false) }

        runCatching {
            shadowDatabase.databaseHelper.writableDatabase.rawQuery(
                "select * from ${DBTable.Favorite.tableName()}",
                null
            ).use {
                assertEquals(contentColumns, it.columnNames.joinToString())
            }
        }
            .onFailure { assertTrue(false) }
            .onSuccess { assertTrue(true) }
    }
}