package com.hanikorm.forecast_apitest

import android.app.Application
import com.hanikorm.data.repository.WeatherRepositoryImpl
import com.hanikorm.presentation.ui.MainApp

class App : Application(){


    override fun onCreate() {
        MainApp.weatherRepository = WeatherRepositoryImpl()
        super.onCreate()
        instance = this


    }

    companion object {
        lateinit var instance: App
            private set
    }


}