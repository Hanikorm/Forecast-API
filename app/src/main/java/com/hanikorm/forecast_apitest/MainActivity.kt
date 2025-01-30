package com.hanikorm.forecast_apitest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanikorm.forecast_apitest.Adapter.WeatherAdapter
import com.hanikorm.forecast_apitest.Common.Common
import com.hanikorm.forecast_apitest.databinding.ActivityMainBinding
import com.hanikorm.forecast_apitest.Model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherData = mutableListOf<WeatherResponse>()
    private lateinit var adapter: WeatherAdapter
    private val cities = listOf("Moscow", "Bryansk", "Saint Petersburg", "Kazan", "Novosibirsk", "Novozybkov","Yalta","London")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerWeatherList.layoutManager = LinearLayoutManager(this)
        adapter = WeatherAdapter(this, weatherData)
        binding.recyclerWeatherList.adapter = adapter

        getWeatherForCities()
    }

    private fun getWeatherForCities() {
        for (city in cities) {
            getWeather(city)
        }
    }

    private fun getWeather(city: String) {
        Common.retrofitService.getCurrentWeather(city, key)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        weatherData.add(response.body()!!)
                        adapter.notifyDataSetChanged()
                    } else {
                        showError("Ошибка: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    showError("Ошибка: ${t.localizedMessage}")
                }
            })
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}