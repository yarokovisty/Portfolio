package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    operator fun invoke(): CurrentWeatherItem? =
        repository.getCurrentWeather()

    suspend operator fun invoke(lon: Double, lat: Double): Result<CurrentWeatherItem> =
        repository.getCurrentWeather(lon, lat)
}