package com.hanikorm.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hanikorm.domain.model.WeatherResponse // Импортируйте WeatherResponse
import com.hanikorm.presentation.R
import com.squareup.picasso.Picasso

class WeatherAdapter(
    private val context: Context,
    private var weatherData: List<WeatherResponse> = emptyList()
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
        holder.temperature.text = "${weather.main.temp}°C"

        val description = weather.weather.firstOrNull()?.description ?: "N/A" // Безопасное получение описания
        holder.weatherDescription.text = description

        val icon = weather.weather.firstOrNull()?.icon // Безопасное получение иконки
        if (icon != null) {
            Picasso.get()
                .load("https://openweathermap.org/img/wn/${icon}@2x.png")
                .into(holder.weatherIcon)
        }
    }


    override fun getItemCount(): Int = weatherData.size

    fun updateData(newWeatherData: List<WeatherResponse>) {
        val diffResult = DiffUtil.calculateDiff(WeatherDiffCallback(weatherData, newWeatherData))
        weatherData = newWeatherData
        diffResult.dispatchUpdatesTo(this)
    }

    class WeatherDiffCallback(
        private val oldList: List<WeatherResponse>,
        private val newList: List<WeatherResponse>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}