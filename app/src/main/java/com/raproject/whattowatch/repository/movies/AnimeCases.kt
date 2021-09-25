package com.raproject.whattowatch.repository.movies

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.core.AnimeCasesCore
import com.raproject.whattowatch.utils.Localization
import org.koin.core.KoinComponent
import org.koin.core.inject

class AnimeCases : KoinComponent {

    private val database: DataBase by inject()
    private val caseCore: AnimeCasesCore by inject()

    suspend fun getFilms(localization: Localization): List<ContentItem> {
        return when (localization) {
            Localization.English -> database.execute(caseCore.getAnimeENCore)
            Localization.Russian -> database.execute(caseCore.getAnimeRUCore)
        }
    }
}
