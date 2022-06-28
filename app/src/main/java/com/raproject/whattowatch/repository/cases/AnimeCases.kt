package com.raproject.whattowatch.repository.cases

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.cases.core.AnimeCasesCore
import com.raproject.whattowatch.utils.Localization
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AnimeCases : KoinComponent {

    private val database: DataBase by inject()
    private val caseCore: AnimeCasesCore by inject()

    suspend fun getAnime(localization: Localization, orderCommand: String): List<ContentItem> {
        return database.execute(caseCore.contentRequest.invoke(localization, orderCommand))
    }
}
