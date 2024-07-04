package com.example.forecastapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapp.R
import com.example.forecastapp.domain.entity.DailyForecastItem

class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastViewHolder>() {

    var listDailyForecast: List<DailyForecastItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_forecast, parent, false)

        return DailyForecastViewHolder(view)
    }

    override fun getItemCount(): Int =
        listDailyForecast.size

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val item = listDailyForecast[position]
        holder.bind(item)
    }

}