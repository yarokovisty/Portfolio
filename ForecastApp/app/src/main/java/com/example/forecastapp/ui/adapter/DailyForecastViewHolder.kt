package com.example.forecastapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapp.R
import com.example.forecastapp.databinding.ItemDailyForecastBinding
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.util.WeatherUtils

class DailyForecastViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemDailyForecastBinding.bind(view)

    fun bind(dailyForecastItem: DailyForecastItem) = with(binding) {
        tvDateDailyForecast.text = dailyForecastItem.date
        tvTempDailyForecast.text = view.context
            .getString(R.string.template_temperature, dailyForecastItem.temp.toString())
        imgTypeWeatherDailyForecast.setImageResource(WeatherUtils.getIconWeather(dailyForecastItem.id))
    }
}