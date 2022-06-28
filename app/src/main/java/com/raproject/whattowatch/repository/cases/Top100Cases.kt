package com.raproject.whattowatch.repository.cases

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.cases.core.Top100CasesCore
import com.raproject.whattowatch.utils.Localization
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Top100Cases : KoinComponent {

    private val database: DataBase by inject()
    private val caseCore: Top100CasesCore by inject()

    suspend fun getTop100(localization: Localization, orderCommand: String): List<ContentItem> {
        return database.execute(caseCore.contentRequest.invoke(localization, orderCommand))
    }
}
