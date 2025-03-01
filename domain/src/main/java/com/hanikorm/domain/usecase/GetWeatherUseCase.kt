package com.hanikorm.domain.usecase

import com.hanikorm.domain.model.WeatherResponse
import com.hanikorm.domain.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend fun execute(city: String, apiKey: String): WeatherResponse {
        return repository.getWeather(city, apiKey)
    }
}