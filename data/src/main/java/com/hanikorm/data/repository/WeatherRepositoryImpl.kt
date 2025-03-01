package com.hanikorm.data.repository

import com.hanikorm.data.network.RetrofitServices
import com.hanikorm.domain.model.ForecastResponse
import com.hanikorm.domain.model.WeatherResponse
import com.hanikorm.domain.WeatherRepository

class WeatherRepositoryImpl(private val api: RetrofitServices) : WeatherRepository {
    override suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        return api.getCurrentWeather(city, apiKey)
    }

    override suspend fun getForecast(city: String, apiKey: String): ForecastResponse {
        return api.getForecast(city, apiKey)
    }
}