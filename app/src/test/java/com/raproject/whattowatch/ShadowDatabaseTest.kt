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

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from MainTable", null).use {
            assertEquals("contentId, image, title, year, country, duration, devRating, kinopoiskRating, director, cast, description", it.columnNames.joinToString())
        }

        fakeDatabase.databaseHelper.writableDatabase.rawQuery("select * from MainTableEN", null).use {
            assertEquals("contentId, image, title, year, country, duration, devRating, kinopoiskRating, director, cast, description", it.columnNames.joinToString())
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
    }

    @After
    fun tearDown() {
        GlobalContext.stopKoin()
    }
}