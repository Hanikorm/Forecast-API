package com.hanikorm.presentation.ui

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanikorm.data.common.key
import com.hanikorm.presentation.R
import com.hanikorm.presentation.adapter.HourlyForecastAdapter
import com.hanikorm.presentation.databinding.ActivityMainBinding
import com.hanikorm.presentation.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModel()
    private lateinit var forecastAdapter: HourlyForecastAdapter
    private val apiKey = key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // При нажатии на название города открывается диалог поиска
        binding.tvCityAndTime.setOnClickListener {
            showCitySearchDialog()
        }

        // Инициализируем RecyclerView для прогноза (горизонтальный список с reverseLayout)
        forecastAdapter = HourlyForecastAdapter(this)
        binding.recyclerHourlyForecast.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastAdapter
        }

        // Наблюдаем за текущей погодой
        lifecycleScope.launchWhenStarted {
            weatherViewModel.weatherResponse.collectLatest { weather ->
                weather?.let { updateCurrentWeather(it) }
            }
        }

        // Наблюдаем за прогнозом
        lifecycleScope.launchWhenStarted {
            weatherViewModel.forecastResponse.collectLatest { forecast ->
                forecast?.let {
                    // Можно отфильтровать прогноз за текущий день, если нужно
                    forecastAdapter.updateData(it.list)
                }
            }
        }
        val defaultCity = "Novozybkov"
        weatherViewModel.fetchWeather(defaultCity, apiKey)
        weatherViewModel.fetchForecast(defaultCity, apiKey)
    }

    private fun updateCurrentWeather(weather: com.hanikorm.domain.model.WeatherResponse) {
        binding.tvTemperature.text = "${weather.main.temp.toInt()}°"
        binding.tvFeelsLike.text = "ощущается как ${weather.main.feels_like.toInt()}°"
        binding.tvCityAndTime.text = "${weather.name} • ${getCurrentTime()}"

        // Вычисляем совет по погоде
        val advice = when {
            weather.main.temp < 10 -> "одевайтесь теплее"
            weather.main.temp > 20 -> "пейте больше воды"
            else -> "погода нормальная"
        }
        binding.tvInfoMessage.text = advice
        // Получаем значение иконки из первого элемента списка weather
        val icon = weather.weather.firstOrNull()?.icon
        if (!icon.isNullOrEmpty()) {
            val iconUrl = "https://openweathermap.org/img/wn/${icon}@2x.png"
            // Загружаем иконку с помощью Picasso
            Picasso.get().load(iconUrl).into(binding.imgWeatherIcon)
        } else {
            // Если иконка отсутствует, можно установить стандартное изображение
            binding.imgWeatherIcon.setImageResource(R.drawable.ic_cloud)
        }
    }


    private fun getCurrentTime(): String {
        val sdf = java.text.SimpleDateFormat("EEE, HH:mm", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }

    private fun showCitySearchDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Поиск города")
        val input = EditText(this)
        input.hint = "Введите название города"
        builder.setView(input)
        builder.setPositiveButton("OK") { _, _ ->
            val city = input.text.toString().trim()
            if (city.isNotEmpty()) {
                weatherViewModel.fetchWeather(city, apiKey)
                weatherViewModel.fetchForecast(city, apiKey)
            }
        }
        builder.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}
