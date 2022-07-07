@file:OptIn(ExperimentalCoroutinesApi::class)
package com.raproject.whattowatch.repository.request

import com.raproject.whattowatch.ShadowDatabase
import com.raproject.whattowatch.repository.DataBase
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
internal class GetSmallPosterByIdTest : KoinTest {

    private val db: DataBase by inject()

    @Test
    fun `runRequest() poster not found`() = runTest {
        val request = get<GetSmallPosterById>{
            parametersOf(GetSmallPosterById.createParams(contentId = "4"))
        }

        val  task = async {  db.execute(request) }

        runCurrent()

        assertEquals(null, task.await().getOrNull())

    }

    @Test
    fun `runRequest() poster has been found`() = runTest {
        val request = get<GetSmallPosterById>{
            parametersOf(GetSmallPosterById.createParams(contentId = "3"))
        }

        val  task = async {  db.execute(request) }

        runCurrent()

        assertNotEquals(null, task.await().getOrNull())

    }

    @After
    fun tearDown() {
        GlobalContext.stopKoin()
    }
}