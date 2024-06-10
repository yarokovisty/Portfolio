package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.entity.CurrentForecastItem
import com.example.weatherapp.domain.repository.ForecastRepository

class GetCurrentForecastUseCase(private val repository: ForecastRepository) {

    suspend operator fun invoke(lat: Double, lon: Double): CurrentForecastItem =
        repository.getCurrentForecast(lat, lon)
}