package com.raproject.whattowatch.di

import com.raproject.whattowatch.ui.movies.MoviesFragmentViewModel
import com.raproject.whattowatch.ui.series.SeriesFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

var vmModule = module {
    viewModel { MoviesFragmentViewModel() }
    viewModel { SeriesFragmentViewModel() }
}

