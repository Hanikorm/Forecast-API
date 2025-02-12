package com.hanikorm.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanikorm.domain.WeatherRepository
import com.hanikorm.domain.model.WeatherResponse
import com.hanikorm.domain.usecase.GetWeatherUseCase
import com.hanikorm.presentation.adapter.WeatherAdapter
import com.hanikorm.presentation.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var repository: WeatherRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private val scope = CoroutineScope(Dispatchers.Default)
    private lateinit var binding: ActivityMainBinding
    private var weatherData = mutableListOf<WeatherResponse>()
    private lateinit var adapter: WeatherAdapter
    private val cities = listOf(
        "Moscow",
        "Bryansk",
        "Saint Petersburg",
        "Kazan",
        "Novosibirsk",
        "Novozybkov",
        "Yalta",
        "London"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = MainApp.weatherRepository
        getWeatherUseCase = GetWeatherUseCase(repository)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.recyclerWeatherList.layoutManager = LinearLayoutManager(this)

        weatherData = mutableListOf()
        adapter = WeatherAdapter(this, weatherData)
        binding.recyclerWeatherList.adapter = adapter

        getWeatherForCities()
    }

    private fun getWeatherForCities() {
        scope.launch {

            for (city in cities) {
                val weather = getWeatherUseCase.execute(city)
                withContext(Dispatchers.Main) {
                    weatherData.add(weather)
                    adapter.updateData(weatherData.toList())
                }
            }


        }


    }
}