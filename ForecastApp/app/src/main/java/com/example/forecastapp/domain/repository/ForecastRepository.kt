package com.example.forecastapp.domain.repository

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.Result

interface ForecastRepository {

    suspend fun getCurrentWeather(lon: Double, lat: Double): Result<CurrentWeatherItem>
}