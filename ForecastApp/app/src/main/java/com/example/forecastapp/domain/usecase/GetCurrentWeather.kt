package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class GetCurrentWeather @Inject constructor(
    private val repository: ForecastRepository
) {

    suspend operator fun invoke(lon: Double, lat: Double): CurrentWeatherItem =
        repository.getCurrentWeather(lon, lat)
}