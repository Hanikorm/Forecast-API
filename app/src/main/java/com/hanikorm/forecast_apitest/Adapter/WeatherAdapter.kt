package com.hanikorm.forecast_apitest.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hanikorm.forecast_apitest.R
import com.hanikorm.forecast_apitest.Model.WeatherResponse
import com.squareup.picasso.Picasso

class WeatherAdapter(
    private val context: Context,
    private val weatherData: List<WeatherResponse>
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.txt_city_name)
        val weatherDescription: TextView = itemView.findViewById(R.id.txt_weather_description)
        val temperature: TextView = itemView.findViewById(R.id.txt_temperature)
        val weatherIcon: ImageView = itemView.findViewById(R.id.img_weather_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherData[position]

        holder.cityName.text = weather.name
        holder.weatherDescription.text = weather.weather[0].description
        holder.temperature.text = "${weather.main.temp}°C"
        Picasso.get().load("https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png")
            .into(holder.weatherIcon)
    }

    override fun getItemCount(): Int = weatherData.size
}