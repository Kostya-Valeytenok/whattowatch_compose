package com.raproject.whattowatch.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(listOf(dataBaseCasesModule, vmModule, dbRequestsModule,useCasesModel))
        }
    }
}
