package com.raproject.whattowatch.repository.movies

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.core.MoviesCasesCore
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoviesCases() : KoinComponent {

    private val database: DataBase by inject()
    private val caseCore: MoviesCasesCore by inject()

    suspend fun getFilms(): List<ContentItem> {
        return database.execute(caseCore.moviesENCore)
    }
}
