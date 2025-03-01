package com.hanikorm.domain.model

data class ForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val dt: Long, // Unix-время
    val main: Main,
    val weather: List<Weather>
)

data class WeatherResponse(
    val id: Int,
    val name: String,
    val weather: List<Weather>,
    val main: Main,
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int
)
