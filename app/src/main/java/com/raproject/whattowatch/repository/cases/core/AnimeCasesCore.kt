package com.raproject.whattowatch.repository.cases.core

import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.ContentType
import com.raproject.whattowatch.utils.Localization
import org.koin.core.KoinComponent

class AnimeCasesCore : KoinComponent, BaseCaseCore() {

    override val contentType: ContentType get() = ContentType.Anime
    val getAnimeENCore = this::getContentCoreRequestEN
    val getAnimeRUCore = this::getContentCoreRequestRU

    private suspend fun getContentCoreRequestEN(database: SQLiteDatabase): List<ContentItem> {
        return getContentCoreRequest(database = database, localization = Localization.English)
    }

    private suspend fun getContentCoreRequestRU(database: SQLiteDatabase): List<ContentItem> {
        return getContentCoreRequest(database = database, localization = Localization.Russian)
    }
}
