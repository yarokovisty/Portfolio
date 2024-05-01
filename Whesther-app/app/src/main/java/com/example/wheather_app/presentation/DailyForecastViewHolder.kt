package com.example.wheather_app.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wheather_app.R
import com.example.wheather_app.databinding.ItemDailyForecastBinding
import com.example.wheather_app.domain.entity.HourlyWeatherItem
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.Locale

class DailyForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemDailyForecastBinding.bind(view)

    fun bind(item: HourlyWeatherItem) {
        with(binding) {
            tvDate.text = templateDate(item.time)
            tvDailyTemp.text = roundTemp(item.temperature)
            imgDailyWeatherType.setImageResource(getIconCurrentWeather(item.type))
        }
    }

    private fun roundTemp(temp: Double) = temp.toInt().toString()

    private fun getIconCurrentWeather(type: Int): Int {
        return when(type) {
            in THUNDERSTORM -> R.drawable.icon_thunderstorm
            in DRIZZLE -> R.drawable.icon_drizzle
            in RAIN -> R.drawable.icon_rain
            in SNOW -> R.drawable.icon_snow
            in ATMOSPHERE -> R.drawable.icon_atmosphere
            CLEAR -> R.drawable.icon_clear
            in CLOUDS -> R.drawable.icon_cloud
            else -> throw RuntimeException("There isn't such type $type")
        }
    }

    private fun templateDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("E, MMM d", Locale.getDefault())
        val date = inputFormat.parse(inputDate)

        return outputFormat.format(date)
    }

    companion object {
        private val THUNDERSTORM = 200..299
        private val DRIZZLE = 300..399
        private val RAIN = 500..599
        private val SNOW = 600..699
        private val ATMOSPHERE = 700..799
        private const val CLEAR = 800
        private val CLOUDS = 801..804
    }
}