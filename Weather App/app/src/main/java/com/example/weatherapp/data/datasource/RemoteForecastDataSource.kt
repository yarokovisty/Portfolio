package com.example.weatherapp.data.datasource

import com.example.weatherapp.data.remote.model.currentforecast.CurrentForecastDto
import com.example.weatherapp.data.remote.model.dailyforecast.DailyForecastDto
import com.example.weatherapp.data.remote.model.hourlyforecast.HourlyForecastDto

interface RemoteForecastDataSource {

    suspend fun getCurrentForecast(lat: Double, lon: Double): CurrentForecastDto

    suspend fun getHourlyForecast(lat: Double, lon: Double): HourlyForecastDto

    suspend fun getDailyForecast(lat: Double, lon: Double): DailyForecastDto

}