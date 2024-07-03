package com.example.forecastapp.domain.repository

import com.example.forecastapp.domain.entity.CurrentWeatherItem

interface ForecastRepository {

    suspend fun getCurrentWeather(lon: Double, lat: Double): CurrentWeatherItem
}