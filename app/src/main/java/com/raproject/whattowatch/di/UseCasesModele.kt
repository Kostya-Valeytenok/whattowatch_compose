package com.raproject.whattowatch.di

import com.raproject.whattowatch.repository.use_case.*
import org.koin.dsl.module

val useCasesModel = module {
    single { GetTop100UseCase(get()) }
    single { GetCartoonsUseCase(get()) }
    single { GetMoviesUseCase(get()) }
    single { GetTVShowsUseCase(get()) }
    single { GetAmineUseCase(get()) }
    single { AddContentToFavoriteUseCase(get()) }
    single { DeleteContentFromFavoriteUseCase(get()) }
    single { GetIsInFavoriteStatusUseCase(get()) }
}
