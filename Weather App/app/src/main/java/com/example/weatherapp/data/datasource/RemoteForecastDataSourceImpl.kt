package com.example.weatherapp.data.datasource

import com.example.weatherapp.data.remote.model.currentforecast.CurrentForecastDto
import com.example.weatherapp.data.remote.model.dailyforecast.DailyForecastDto
import com.example.weatherapp.data.remote.model.hourlyforecast.HourlyForecastDto
import com.example.weatherapp.data.remote.service.ForecastApi

class RemoteForecastDataSourceImpl(private val forecastApi: ForecastApi) : RemoteForecastDataSource {
    override suspend fun getCurrentForecast(lat: Double, lon: Double): CurrentForecastDto =
        forecastApi.getCurrentForecast(lat, lon)

    override suspend fun getHourlyForecast(lat: Double, lon: Double): HourlyForecastDto {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyForecast(lat: Double, lon: Double): DailyForecastDto {
        TODO("Not yet implemented")
    }

}