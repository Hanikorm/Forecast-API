package com.hanikorm.domain

import com.hanikorm.domain.model.WeatherResponse
import com.hanikorm.domain.model.ForecastResponse

interface WeatherRepository {
    suspend fun getWeather(city: String, apiKey: String): WeatherResponse
    suspend fun getForecast(city: String, apiKey: String): ForecastResponse
}