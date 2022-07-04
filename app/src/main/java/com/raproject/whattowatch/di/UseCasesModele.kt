package com.raproject.whattowatch.di

import com.raproject.whattowatch.repository.cases.GetCartoonsUseCase
import com.raproject.whattowatch.repository.cases.GetMoviesUseCase
import com.raproject.whattowatch.repository.cases.GetTVShowsUseCase
import com.raproject.whattowatch.repository.cases.GetTop100UseCase
import org.koin.dsl.module

val useCasesModel = module {
    single { GetTop100UseCase(get()) }
    single { GetCartoonsUseCase(get()) }
    single { GetMoviesUseCase(get()) }
    single { GetTVShowsUseCase(get()) }
}
