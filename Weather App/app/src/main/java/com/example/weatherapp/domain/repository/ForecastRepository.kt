package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.entity.CurrentForecastItem
import com.example.weatherapp.domain.entity.DailyForecastItem
import com.example.weatherapp.domain.entity.HourlyForecastItem

interface ForecastRepository {

    suspend fun getCurrentForecast(lat: Double, lon: Double): CurrentForecastItem

    suspend fun getHourlyForecast(lat: Double, lon: Double): List<HourlyForecastItem>

    suspend fun getDailyForecast(lat: Double, lon: Double): List<DailyForecastItem>
}