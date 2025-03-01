package com.hanikorm.forecast_apitest

import android.app.Application
import com.hanikorm.forecast_apitest.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Инициализация Koin с передачей контекста и модуля
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
