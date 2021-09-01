package com.raproject.whattowatch.di

import com.raproject.whattowatch.ui.movies.MoviesFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

var moviesModule = module {
    viewModel { MoviesFragmentViewModel() }
}
