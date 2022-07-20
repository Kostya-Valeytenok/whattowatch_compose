package com.raproject.whattowatch.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.raproject.whattowatch.models.OldUserData
import com.raproject.whattowatch.repository.*
import com.raproject.whattowatch.repository.app_settings.AppSettings
import com.raproject.whattowatch.repository.app_settings.SettingRepository
import com.raproject.whattowatch.repository.billing.BillingCore
import com.raproject.whattowatch.repository.billing.BillingManager
import com.raproject.whattowatch.utils.ContentProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

var dataBaseCasesModule = module {
    single { DatabaseHelper(androidApplication()) }
    single { DataBase() }
    single { RequestManager() }
    single { Firebase.storage }
    single { AppSettings(androidContext()) }

    single { ContentViewRepository(get()) }
    single { ContentDetailsRepository(get()) }
    single { SettingRepository(get()) }

    single { BillingCore(androidContext()) }
    single { BillingManager() }

    factory { params -> OldUserData(params[0], params[1]) }
    factory { ContentProvider() }
}
