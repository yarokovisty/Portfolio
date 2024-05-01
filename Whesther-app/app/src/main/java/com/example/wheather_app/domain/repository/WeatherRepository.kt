package com.example.wheather_app.domain.repository

import com.example.wheather_app.domain.entity.CurrentWeatherItem
import com.example.wheather_app.domain.entity.HourlyWeatherItem

interface WeatherRepository {

    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherItem

    suspend fun getHourlyWeather(lat: Double, lon: Double): List<HourlyWeatherItem>
}