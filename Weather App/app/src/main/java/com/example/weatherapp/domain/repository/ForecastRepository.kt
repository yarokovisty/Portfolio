package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.entity.CurrentForecastItem
import com.example.weatherapp.domain.entity.DailyForecastItem
import com.example.weatherapp.domain.entity.HourlyForecastItem

interface ForecastRepository {

    suspend fun getCurrentForecast(): CurrentForecastItem

    suspend fun getHourlyForecast(): List<HourlyForecastItem>

    suspend fun getDailyForecast(): List<DailyForecastItem>
}