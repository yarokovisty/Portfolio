package com.example.wheather_app.domain.usecase

import com.example.wheather_app.domain.entity.HourlyWeatherItem
import com.example.wheather_app.domain.repository.WeatherRepository

class GetHourlyWeatherUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke(lat: Double, lon: Double): List<HourlyWeatherItem> {
        return repository.getHourlyWeather(lat, lon)
    }
}