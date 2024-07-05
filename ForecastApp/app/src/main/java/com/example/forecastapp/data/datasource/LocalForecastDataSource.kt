package com.example.forecastapp.data.datasource

import com.example.forecastapp.data.database.model.HourlyForecastDbModel
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.HourlyForecastItem

interface LocalForecastDataSource {

    fun saveCurrentWeather(currentWeatherItem: CurrentWeatherItem)

    fun getCurrentWeather(): CurrentWeatherItem?

    suspend fun saveHourlyForecast(listHourlyForecastItem: List<HourlyForecastDbModel>)

    suspend fun getHourlyForecast(): List<HourlyForecastDbModel>

    suspend fun clearHourlyForecast()
}