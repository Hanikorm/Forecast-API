package com.hanikorm.forecast_apitest.Model

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String
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