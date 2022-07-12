package com.raproject.whattowatch.repository

import com.raproject.whattowatch.contentColumns
import com.raproject.whattowatch.genresColumns
import com.raproject.whattowatch.mainTableColumns
import com.raproject.whattowatch.postersColumns
import com.raproject.whattowatch.utils.ContentType
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class DataBaseTest : KoinTest {

    private val fakeDatabase: DatabaseHelper by inject()

    @Test
    fun `database structure tests`(){
        fakeDatabase.writableDatabase.rawQuery("select * from MainTable", null).use {
            assertEquals(mainTableColumns, it.columnNames.joinToString())
        }

        fakeDatabase.writableDatabase.rawQuery("select * from MainTableEN", null).use {
            assertEquals(mainTableColumns, it.columnNames.joinToString())
        }

        fakeDatabase.writableDatabase.rawQuery("select * from Posters", null).use {
            assertEquals(postersColumns, it.columnNames.joinToString())
        }

        fakeDatabase.writableDatabase.rawQuery("select * from GenresKeysEN", null).use {
            assertEquals(genresColumns, it.columnNames.joinToString())
        }

        fakeDatabase.writableDatabase.rawQuery("select * from GenresKeys", null).use {
            assertEquals(genresColumns, it.columnNames.joinToString())
        }

        fakeDatabase.writableDatabase.rawQuery("select * from ${ContentType.Movies.tableName}", null).use {
            assertEquals(contentColumns, it.columnNames.joinToString())
        }

        fakeDatabase.writableDatabase.rawQuery("select * from ${ContentType.TVShows.tableName}", null).use {
            assertEquals(contentColumns, it.columnNames.joinToString())
        }

        fakeDatabase.writableDatabase.rawQuery("select * from ${ContentType.Cartoons.tableName}", null).use {
            assertEquals(contentColumns, it.columnNames.joinToString())
        }

        fakeDatabase.writableDatabase.rawQuery("select * from ${ContentType.Anime.tableName}", null).use {
            assertEquals(contentColumns, it.columnNames.joinToString())
        }
    }

    @After
    fun tearDown() {
        GlobalContext.stopKoin()
    }
}