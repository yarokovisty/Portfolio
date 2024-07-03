package com.example.forecastapp.data.datasource

import com.example.forecastapp.data.network.dto.CurrentWeatherDTO

interface RemoteForecastDataSource {

    suspend fun getCurrentWeather(lon: Double, lat: Double): CurrentWeatherDTO
}