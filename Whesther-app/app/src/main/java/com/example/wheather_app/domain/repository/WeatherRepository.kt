package com.example.wheather_app.domain.repository

import com.example.wheather_app.domain.entity.CurrentWeatherItem

interface WeatherRepository {

    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherItem
}