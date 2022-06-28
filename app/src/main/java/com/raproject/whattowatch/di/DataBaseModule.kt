package com.raproject.whattowatch.di

import com.raproject.whattowatch.models.OldUserData
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.DatabaseHelper
import com.raproject.whattowatch.repository.cases.*
import com.raproject.whattowatch.repository.cases.core.*
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
    single { Top100CasesCore() }
    single { CartoonsCases() }
    single { AnimeCases() }
    single { Top100Cases() }
    single { AnimeCasesCore() }
    single { RequestManager() }
    factory { params -> OldUserData(params[0], params[1]) }
    factory { ContentProvider() }
}
