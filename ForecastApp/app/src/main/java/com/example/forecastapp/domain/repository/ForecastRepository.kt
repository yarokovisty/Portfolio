package com.example.forecastapp.domain.repository

import com.example.forecastapp.data.database.model.DailyForecastDbModel
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.domain.entity.Result

interface ForecastRepository {

    fun saveCurrentWeather(currentWeatherItem: CurrentWeatherItem)

    fun getCurrentWeather(): CurrentWeatherItem?

    suspend fun getCurrentWeather(lon: Double, lat: Double): Result<CurrentWeatherItem>

    suspend fun saveHourlyForecast(listHourlyForecastItem: List<HourlyForecastItem>)

    suspend fun getHourlyForecast(): List<HourlyForecastItem>

    suspend fun getHourlyForecast(lon: Double, lat: Double): Result<List<HourlyForecastItem>>

    suspend fun clearHourlyForecast()

    suspend fun getDailyForecast(lon: Double, lat: Double): Result<List<DailyForecastItem>>

    suspend fun getDailyForecast(): List<DailyForecastItem>

    suspend fun saveDailyForecast(listDailyForecastItem: List<DailyForecastItem>)

    suspend fun clearDailyForecast()
}