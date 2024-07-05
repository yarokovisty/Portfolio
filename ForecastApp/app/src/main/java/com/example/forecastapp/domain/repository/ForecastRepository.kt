package com.example.forecastapp.domain.repository

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.domain.entity.Result

interface ForecastRepository {

    fun saveCurrentWeather(currentWeatherItem: CurrentWeatherItem)

    fun getCurrentWeather(): CurrentWeatherItem?

    suspend fun getCurrentWeather(lon: Double, lat: Double): Result<CurrentWeatherItem>

    suspend fun getHourlyForecast(lon: Double, lat: Double): Result<List<HourlyForecastItem>>

    suspend fun getDailyForecast(lon: Double, lat: Double): Result<List<DailyForecastItem>>
}