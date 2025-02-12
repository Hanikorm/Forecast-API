package com.hanikorm.domain.usecase

import com.hanikorm.domain.WeatherRepository
import com.hanikorm.domain.model.WeatherResponse

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend fun execute(city: String): WeatherResponse {
        return repository.getWeather(city)
    }
}