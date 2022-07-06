@file:OptIn(ExperimentalCoroutinesApi::class)

package com.raproject.whattowatch.repository.request

import com.raproject.whattowatch.ShadowDatabase
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.utils.DBTable
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.OrderType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.get
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowDatabase::class])
class GetTop100Test : KoinTest {

    private val db:DataBase by inject()

    @Test
    fun`runRequest() check for Russian Localization and order by DevRating`() = runTest {

        val request = get<GetTop100>{
            parametersOf(GetTop100.createParams(
                localization = Localization.Russian,
                orderCommand = OrderType.DescendingOrder.by(DBTable.MainTable.DevRating)
            ))
        }

        val  task = async {  db.execute(request) }

        runCurrent()

        assertEquals("3, 1, 2",task.await())
    }

    @Test
    fun`runRequest() check for Russian Localization and order by KinopoiskRating`() = runTest {

        val request = get<GetTop100>{
            parametersOf(GetTop100.createParams(
                localization = Localization.Russian,
                orderCommand = OrderType.DescendingOrder.by(DBTable.MainTable.KinopoiskRating)
            ))
        }


        val  task = async {  db.execute(request) }

        runCurrent()

        assertEquals("1, 2, 3",task.await())
    }

    @Test
    fun`runRequest() check for English Localization and order by DevRating`() = runTest {

        val request = get<GetTop100>{
            parametersOf(GetTop100.createParams(
                localization = Localization.English,
                orderCommand = OrderType.DescendingOrder.by(DBTable.MainTable.DevRating)
            ))
        }

        val  task = async {  db.execute(request) }

        runCurrent()

        assertEquals("3, 1, 2",task.await())
    }

    @Test
    fun`runRequest() check for English Localization and order by KinopoiskRating`() = runTest {

        val request = get<GetTop100>{
            parametersOf(GetTop100.createParams(
                localization = Localization.English,
                orderCommand = OrderType.DescendingOrder.by(DBTable.MainTable.KinopoiskRating)
            ))
        }

        val  task = async {  db.execute(request) }

        runCurrent()

        assertEquals("1, 2, 3",task.await())
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}

