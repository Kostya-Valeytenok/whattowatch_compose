package com.raproject.whattowatch.repository.series

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.core.SeriesCasesCore
import org.koin.core.KoinComponent
import org.koin.core.inject

class SeriesCases : KoinComponent {

    private val database: DataBase by inject()
    private val caseCore: SeriesCasesCore by inject()

    suspend fun getSeries(): List<ContentItem> {
        return database.execute(caseCore.seriesENCore)
    }
}
