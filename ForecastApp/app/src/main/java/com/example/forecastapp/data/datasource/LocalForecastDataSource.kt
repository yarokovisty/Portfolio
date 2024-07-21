package com.example.forecastapp.data.datasource

import com.example.forecastapp.data.database.model.DailyForecastDbModel
import com.example.forecastapp.data.database.model.HourlyForecastDbModel
import com.example.forecastapp.domain.entity.CurrentWeatherItem

interface LocalForecastDataSource {

    fun saveCurrentWeather(currentWeatherItem: CurrentWeatherItem)

    fun getCurrentWeather(): CurrentWeatherItem?

    suspend fun saveHourlyForecast(listHourlyForecastDbModel: List<HourlyForecastDbModel>)

    suspend fun getHourlyForecast(): List<HourlyForecastDbModel>

    suspend fun clearHourlyForecast()

    suspend fun saveDailyForecast(listDailyForecastDbModel: List<DailyForecastDbModel>)

    suspend fun getDailyForecast(): List<DailyForecastDbModel>

    suspend fun clearDailyForecast()
}