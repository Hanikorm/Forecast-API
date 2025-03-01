package com.hanikorm.domain.usecase

import com.hanikorm.domain.model.ForecastResponse
import com.hanikorm.domain.WeatherRepository

class GetForecastUseCase(private val repository: WeatherRepository) {
    suspend fun execute(city: String, apiKey: String): ForecastResponse {
        return repository.getForecast(city, apiKey)
    }
}