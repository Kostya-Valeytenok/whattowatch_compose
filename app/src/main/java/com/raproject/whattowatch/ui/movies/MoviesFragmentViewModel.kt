package com.raproject.whattowatch.ui.movies

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.movies.MoviesCases
import com.raproject.whattowatch.utils.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoviesFragmentViewModel : BaseViewModel(), KoinComponent {

    private val moviesCases: MoviesCases by inject()

    var movies: MutableState<List<ContentItem>> = mutableStateOf(mutableListOf())
    var moviesLoadingStatus: MutableState<Boolean> = mutableStateOf(false)

    fun getMovies() {
        viewModelScope.launch {
            moviesLoadingStatus.value = true
            movies.value = moviesCases.getFilms()
            moviesLoadingStatus.value = false
        }
    }
}
