package com.hanikorm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanikorm.domain.model.ForecastResponse
import com.hanikorm.domain.model.WeatherResponse
import com.hanikorm.domain.usecase.GetForecastUseCase
import com.hanikorm.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _weatherResponse = MutableStateFlow<WeatherResponse?>(null)
    val weatherResponse: StateFlow<WeatherResponse?> get() = _weatherResponse

    private val _forecastResponse = MutableStateFlow<ForecastResponse?>(null)
    val forecastResponse: StateFlow<ForecastResponse?> get() = _forecastResponse

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                _weatherResponse.value = getWeatherUseCase.execute(city, apiKey)
            } catch (e: Exception) {
                _weatherResponse.value = null
            }
        }
    }

    fun fetchForecast(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                _forecastResponse.value = getForecastUseCase.execute(city, apiKey)
            } catch (e: Exception) {
                _forecastResponse.value = null
            }
        }
    }
}
