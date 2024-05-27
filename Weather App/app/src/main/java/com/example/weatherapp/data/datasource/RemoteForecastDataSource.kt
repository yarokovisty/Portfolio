package com.example.weatherapp.data.datasource

import com.example.weatherapp.data.remote.model.CurrentForecastDto

interface RemoteForecastDataSource {

    suspend fun getCurrentForecast(): CurrentForecastDto

    suspend fun getHourlyForecast():
}