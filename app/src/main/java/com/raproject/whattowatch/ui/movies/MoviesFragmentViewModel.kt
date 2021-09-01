package com.raproject.whattowatch.ui.movies

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.movies.MoviesCases
import com.raproject.whattowatch.utils.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoviesFragmentViewModel : BaseViewModel(), KoinComponent {

    private val moviesCases: MoviesCases by inject()

    suspend fun getMovies(): List<ContentItem> {
        return moviesCases.getFilms()
    }
}
