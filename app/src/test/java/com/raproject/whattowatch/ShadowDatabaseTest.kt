package com.raproject.whattowatch

import com.raproject.whattowatch.utils.ContentType
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class ShadowDatabaseTest {

    val fakeDatabase = ShadowDatabase()

    @Test
    fun dbInit() {

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from MainTable", null).use {
            assertEquals(mainTableColumns, it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from MainTableEN", null).use {
            assertEquals(mainTableColumns, it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from Posters", null).use {
            assertEquals(postersColumns, it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from GenresKeysEN", null).use {
            assertEquals(genresColumns, it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from GenresKeys", null).use {
            assertEquals(genresColumns, it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from ${ContentType.Movies.tableName}", null).use {
            assertEquals(contentColumns, it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from ${ContentType.TVShows.tableName}", null).use {
            assertEquals(contentColumns, it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from ${ContentType.Cartoons.tableName}", null).use {
            assertEquals(contentColumns, it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from ${ContentType.Anime.tableName}", null).use {
            assertEquals(contentColumns, it.columnNames.joinToString())
        }
    }

    @Test
    fun dbContentTest(){
        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from MainTable", null).use {
            assertEquals(3,it.count)
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from MainTableEN", null).use {
            assertEquals(3,it.count)
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from Posters", null).use {
            assertEquals(3,it.count)
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from GenresKeysEN", null).use {
            assertEquals(3,it.count)
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from GenresKeys", null).use {
            assertEquals(3,it.count)
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from ${ContentType.Movies.tableName}", null).use {
            assertEquals(3,it.count)
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from ${ContentType.Anime.tableName}", null).use {
            assertEquals(0,it.count)
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from ${ContentType.TVShows.tableName}", null).use {
            assertEquals(0,it.count)
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from ${ContentType.Cartoons.tableName}", null).use {
            assertEquals(0,it.count)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}