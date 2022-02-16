package com.raproject.whattowatch.di

import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.models.OldUserData
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.DatabaseHelper
import com.raproject.whattowatch.repository.cases.AnimeCases
import com.raproject.whattowatch.repository.cases.CartoonsCases
import com.raproject.whattowatch.repository.cases.MoviesCases
import com.raproject.whattowatch.repository.cases.SeriesCases
import com.raproject.whattowatch.repository.cases.core.AnimeCasesCore
import com.raproject.whattowatch.repository.cases.core.CartoonsCasesCore
import com.raproject.whattowatch.repository.cases.core.MoviesCasesCore
import com.raproject.whattowatch.repository.cases.core.SeriesCasesCore
import com.raproject.whattowatch.utils.ContentProvider
import com.raproject.whattowatch.utils.RequestManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var dataBaseCasesModule = module {
    single { DatabaseHelper(androidApplication()) }
    single { SeriesCases() }
    single { SeriesCasesCore() }
    single { DataBase() }
    single { MoviesCases() }
    single { MoviesCasesCore() }
    single { CartoonsCasesCore() }
    single { CartoonsCases() }
    single { AnimeCases() }
    single { AnimeCasesCore() }
    single { RequestManager() }
    factory { params -> ContentItem(params[0], params[1], params[2], params[3], params[4]) }
    factory { params -> OldUserData(params[0], params[1]) }
    factory { ContentProvider() }
}
