package com.hanikorm.data.repository

import com.hanikorm.data.common.Common
import com.hanikorm.data.common.key
import com.hanikorm.domain.WeatherRepository
import com.hanikorm.domain.model.WeatherResponse

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getWeather(city: String): WeatherResponse {
        return Common.retrofitService.getCurrentWeather(
            city = city,
            apiKey = key,
        )


    }
}