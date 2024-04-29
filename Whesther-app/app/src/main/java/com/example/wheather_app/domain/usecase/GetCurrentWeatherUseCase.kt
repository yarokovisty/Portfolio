package com.example.wheather_app.domain.usecase

import com.example.wheather_app.data.retrofit.WeatherRepositoryImpl
import com.example.wheather_app.data.retrofit.dto.CurrentWeatherDto

class GetCurrentWeatherUseCase(private val repository: WeatherRepositoryImpl) {

    suspend operator fun invoke(lat: Double, lon: Double): CurrentWeatherDto {
        return repository.getCurrentWeatherDto(lat, lon)
    }
}