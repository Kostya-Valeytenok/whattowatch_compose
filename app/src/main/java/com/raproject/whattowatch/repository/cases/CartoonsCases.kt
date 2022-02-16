package com.raproject.whattowatch.repository.cases

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.cases.core.CartoonsCasesCore
import com.raproject.whattowatch.utils.Localization
import org.koin.core.KoinComponent
import org.koin.core.inject

class CartoonsCases : KoinComponent {

    private val database: DataBase by inject()
    private val caseCore: CartoonsCasesCore by inject()

    suspend fun getCartoons(localization: Localization): List<ContentItem> {
        return database.execute(caseCore.contentRequest.invoke(localization))
    }
}