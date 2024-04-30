package com.example.wheather_app.domain.usecase

import com.example.wheather_app.data.retrofit.WeatherRepositoryImpl
import com.example.wheather_app.data.retrofit.dto.CurrentWeatherDto
import com.example.wheather_app.domain.entity.CurrentWeatherItem
import com.example.wheather_app.domain.repository.WeatherRepository

class GetCurrentWeatherUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke(lat: Double, lon: Double): CurrentWeatherItem {
        return repository.getCurrentWeather(lat, lon)
    }
}