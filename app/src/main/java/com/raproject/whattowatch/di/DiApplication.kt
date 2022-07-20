package com.raproject.whattowatch.di

import android.app.Application
import com.raproject.whattowatch.repository.billing.BillingManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class DiApplication : Application(), KoinComponent {

    private val billingManager: BillingManager by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(listOf(dataBaseCasesModule, vmModule, dbRequestsModule, useCasesModel))
        }

        billingManager.init()
    }
}
