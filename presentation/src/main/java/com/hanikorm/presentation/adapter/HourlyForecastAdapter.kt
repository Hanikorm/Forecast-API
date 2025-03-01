package com.hanikorm.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hanikorm.domain.model.ForecastItem
import com.hanikorm.presentation.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class HourlyForecastAdapter(
    private val context: Context,
    private var forecastList: List<ForecastItem> = emptyList()
) : RecyclerView.Adapter<HourlyForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeText: TextView = itemView.findViewById(R.id.txt_time)
        val temperatureText: TextView = itemView.findViewById(R.id.txt_temperature)
        val iconImage: ImageView = itemView.findViewById(R.id.img_weather_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_hourly_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastItem = forecastList[position]
        // Преобразуем Unix-время в формат "HH:mm"
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(forecastItem.dt * 1000))
        holder.timeText.text = time
        holder.temperatureText.text = "${forecastItem.main.temp.toInt()}°C"
        forecastItem.weather.firstOrNull()?.icon?.let { icon ->
            val iconUrl = "https://openweathermap.org/img/wn/${icon}@2x.png"
            Picasso.get().load(iconUrl).into(holder.iconImage)
        }
    }

    override fun getItemCount(): Int = forecastList.size

    fun updateData(newForecast: List<ForecastItem>) {
        forecastList = newForecast
        notifyDataSetChanged()
    }
}
