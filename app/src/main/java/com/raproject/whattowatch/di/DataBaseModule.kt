package com.raproject.whattowatch.di

import com.raproject.whattowatch.models.OldUserData
import com.raproject.whattowatch.repository.*
import com.raproject.whattowatch.utils.ContentProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var dataBaseCasesModule = module {
    single { DatabaseHelper(androidApplication()) }
    single { ContentViewRepository(get()) }
    single { ContentDetailsRepository(get()) }
    single { DataBase() }
    single { RequestManager() }
    factory { params -> OldUserData(params[0], params[1]) }
    factory { ContentProvider() }
}
