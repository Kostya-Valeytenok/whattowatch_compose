@file:OptIn(ExperimentalCoroutinesApi::class)

package com.raproject.whattowatch.repository.request

import android.os.Bundle
import com.raproject.whattowatch.ShadowDatabase
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.utils.*
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
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowDatabase::class])
class GetContentCardsByTypeTest : KoinTest {

    private val db: DataBase by inject()

    private val getPosterRequest = get<GetSmallPosterById> {
        parametersOf(GetSmallPosterById.createParams(contentId = "1"))
    }

    @Test
    fun `wrong get content by type request`() = runTest {
        val request = get<GetContentCardsByType> {
            parametersOf(
                Bundle()
            )
        }
        val result = db.execute(request).getOrNull()

        assertEquals(null,result)
    }

    @Test
    fun `get Anime Test`() = runTest{
        val request = get<GetContentCardsByType> {
            parametersOf(
                GetContentCardsByType.createParams(
                    contentType = ContentType.Anime,
                    localization = Localization.English,
                    orderCommand = ""
                )
            )
        }

        db.execute(request).onSuccess { assertTrue(it.isEmpty()) }.onFailure { assertTrue(false) }
    }

    @Test
    fun `get TVShows Test`() = runTest{
        val request = get<GetContentCardsByType> {
            parametersOf(
                GetContentCardsByType.createParams(
                    contentType = ContentType.TVShows,
                    localization = Localization.English,
                    orderCommand = ""
                )
            )
        }

        db.execute(request).onSuccess { assertTrue(it.isEmpty()) }.onFailure { assertTrue(false) }
    }

    @Test
    fun `get Cartoons Test`() = runTest{
        val request = get<GetContentCardsByType> {
            parametersOf(
                GetContentCardsByType.createParams(
                    contentType = ContentType.Cartoons,
                    localization = Localization.English,
                    orderCommand = ""
                )
            )
        }
        val result = db.execute(request
        )
        assertEquals(listOf(),result.getOrNull())

        db.execute(request).onSuccess { assertTrue(it.isEmpty()) }.onFailure { assertTrue(false) }
    }

    @Test
    fun `get Top 100 DEV Rating Test`() = runTest{
        val request = get<GetContentCardsByType> {
            parametersOf(
                GetContentCardsByType.createParams(
                    contentType = ContentType.Top100,
                    localization = Localization.English,
                    orderCommand = OrderType.DescendingOrder.by(DBTable.MainTable.DevRating)
                )
            )
        }
        val poster = db.execute(getPosterRequest)
        val result = db.execute(request)

        val contentItemsList = listOf(
            ContentItem(
                key = 3,
                image = poster.getOrNull(),
                name = "Eternal Sunshine of the Spotless Mind",
                year = "2004",
                genres = "Drama, Melodrama, Fantastic",
                rating = "9.6"
            ),
            ContentItem(
                key = 1,
                image = poster.getOrNull(),
                name = "Inception",
                year = "2010",
                genres = "Action, Detective, Drama, Thriller, Fantastic",
                rating = "9.3"
            ),
            ContentItem(
                key = 2,
                image = poster.getOrNull(),
                name = "Interstellar",
                year = "2014",
                genres = "Drama, Adventure, Fantastic",
                rating = "8.9"
            ),
        )

        assertEquals(contentItemsList,result.getOrNull())
    }

    @Test
    fun `get movies for English Localization`() = runTest {
        val request = get<GetContentCardsByType> {
            parametersOf(
                GetContentCardsByType.createParams(
                    contentType = ContentType.Movies,
                    localization = Localization.English,
                    orderCommand = ""
                )
            )
        }

        val poster = db.execute(getPosterRequest)
        val task = async { db.execute(request) }

        val contentItemsList = listOf(
            ContentItem(
                key = 1,
                image = poster.getOrNull(),
                name = "Inception",
                year = "2010",
                genres = "Action, Detective, Drama, Thriller, Fantastic"
            ),
            ContentItem(
                key = 2,
                image = poster.getOrNull(),
                name = "Interstellar",
                year = "2014",
                genres = "Drama, Adventure, Fantastic"
            ),
            ContentItem(
                key = 3,
                image = poster.getOrNull(),
                name = "Eternal Sunshine of the Spotless Mind",
                year = "2004",
                genres = "Drama, Melodrama, Fantastic"
            ),
        )

        runCurrent()

        assertEquals(contentItemsList, task.await().getOrNull())
    }

    @Test
    fun `get movies for Russian Localization`() = runTest {
        val request = get<GetContentCardsByType> {
            parametersOf(
                GetContentCardsByType.createParams(
                    contentType = ContentType.Movies,
                    localization = Localization.Russian,
                    orderCommand = ""
                )
            )
        }

        val getPosterRequest = get<GetSmallPosterById> {
            parametersOf(GetSmallPosterById.createParams(contentId = "1"))
        }

        val poster = db.execute(getPosterRequest)
        val task = async { db.execute(request) }

        val contentItemsList = listOf(
            ContentItem(
                key = 1,
                image = poster.getOrNull(),
                name = "Начало",
                year = "2010",
                genres = "Боевик, Детектив, Драма, Триллер, Фантастика"
            ),
            ContentItem(
                key = 2,
                image = poster.getOrNull(),
                name = "Интерстеллар",
                year = "2014",
                genres = "Драма, Приключения, Фантастика"
            ),
            ContentItem(
                key = 3,
                image = poster.getOrNull(),
                name = "Вечное сияние чистого разума",
                year = "2004",
                genres = "Драма, Мелодрама, Фантастика"
            ),
        )

        runCurrent()

        assertEquals(contentItemsList, task.await().getOrNull())
    }

    @After
    fun tearDown() {
        GlobalContext.stopKoin()
    }
}