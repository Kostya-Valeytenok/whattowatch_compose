package com.raproject.whattowatch.di

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.DatabaseHelper
import com.raproject.whattowatch.repository.core.AnimeCasesCore
import com.raproject.whattowatch.repository.core.MoviesCasesCore
import com.raproject.whattowatch.repository.movies.AnimeCases
import com.raproject.whattowatch.repository.movies.MoviesCases
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var dataBaseCasesModule = module {
    single { DatabaseHelper(androidApplication()) }
    single { DataBase() }
    single { MoviesCases() }
    single { MoviesCasesCore() }
    single { AnimeCases() }
    single { AnimeCasesCore() }
    factory { params -> ContentItem(params[0], params[1], params[2], params[3], params[4]) }
}