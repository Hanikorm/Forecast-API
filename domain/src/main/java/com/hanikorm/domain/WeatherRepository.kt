package com.hanikorm.domain

import com.hanikorm.domain.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(city: String): WeatherResponse

}