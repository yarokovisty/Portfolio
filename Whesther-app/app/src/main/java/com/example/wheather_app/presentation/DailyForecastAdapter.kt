package com.example.wheather_app.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wheather_app.R
import com.example.wheather_app.domain.entity.HourlyWeatherItem

class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastViewHolder>() {
    var dailyForecastList = listOf<HourlyWeatherItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)

        return DailyForecastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dailyForecastList.size
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val item = dailyForecastList[position]

        holder.bind(item)
    }
}