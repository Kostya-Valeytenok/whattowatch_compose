package com.raproject.whattowatch

import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class ShadowDatabaseTest {

    val fakeDatabase = ShadowDatabase()

    @Test
    fun dbInit() {

        val mainTableColumns ="contentId, image, title, year, country, duration, devRating, kinopoiskRating, director, cast, description"
        val postersColumns ="contentId, image"
        val genresColumns ="contentId, Biograph, Action, Western, Military, Detctive, Document, Drama, Historical, Comedy, Crime, Melodrama, Musical, Adventure, Family, Sport, Thriller, Horror, Fantastic, Fantasy"

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
    }

    @After
    fun tearDown() {
        GlobalContext.stopKoin()
    }
}