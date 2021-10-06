package com.raproject.whattowatch.repository.cases.core

import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.ContentType
import com.raproject.whattowatch.utils.Localization
import org.koin.core.KoinComponent

class SeriesCasesCore : KoinComponent, BaseCaseCore() {

    override val contentType: ContentType get() = ContentType.TVShows
    val getTVShowsENCore = this::getContentCoreRequestEN
    val getTVShowsRUCore = this::getContentCoreRequestRU

    private suspend fun getContentCoreRequestEN(database: SQLiteDatabase): List<ContentItem> {
        return getContentCoreRequest(database = database, localization = Localization.English)
    }

    private suspend fun getContentCoreRequestRU(database: SQLiteDatabase): List<ContentItem> {
        return getContentCoreRequest(database = database, localization = Localization.Russian)
    }
}
