@file:OptIn(ExperimentalCoroutinesApi::class)
package com.raproject.whattowatch.repository.request

import com.raproject.whattowatch.ShadowDatabase
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowDatabase::class])
internal class GetGenresByIdTest: KoinTest {

    private val db: DataBase by inject()

    @Test
    fun `runRequest() check for Russian Localization`() = runTest {
        val request = get<GetGenresById>{
            parametersOf(GetGenresById.createParams(contentId = "1", localization = Localization.Russian))
        }

        val  task = async {  db.execute(request) }

        runCurrent()

        assertEquals("Боевик, Детектив, Драма, Триллер, Фантастика", task.await().getOrNull())
    }

    @Test
    fun `runRequest() check for English Localization`() = runTest{
        val request = get<GetGenresById>{
            parametersOf(GetGenresById.createParams(contentId = "1", localization = Localization.English))
        }

        val  task = async {  db.execute(request) }

        runCurrent()

        assertEquals("Action, Detective, Drama, Thriller, Fantastic", task.await().getOrNull())
    }

    @Test
    fun `runRequest() Not found case`() = runTest{
        val request = get<GetGenresById>{
            parametersOf(GetGenresById.createParams(contentId = "4", localization = Localization.English))
        }

        val  task = async {  db.execute(request) }

        runCurrent()

        assertEquals(null, task.await().getOrNull())
    }

    @Test
    fun `runRequest() content Not equals test found case`() = runTest{
        val request1 = get<GetGenresById>{
            parametersOf(GetGenresById.createParams(contentId = "1", localization = Localization.English))
        }

        val request2 = get<GetGenresById>{
            parametersOf(GetGenresById.createParams(contentId = "2", localization = Localization.English))
        }

        val  task1 = async {  db.execute(request1) }
        val  task2 = async {  db.execute(request2) }

        runCurrent()

        assertNotEquals(task1.await().getOrNull(), task2.await().getOrNull())
    }

    @After
    fun tearDown() {
        GlobalContext.stopKoin()
    }
}