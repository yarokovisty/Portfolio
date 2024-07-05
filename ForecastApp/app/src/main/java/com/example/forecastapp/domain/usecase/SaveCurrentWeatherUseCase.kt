package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class SaveCurrentWeatherUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    operator fun invoke(currentWeatherItem: CurrentWeatherItem) {
        repository.saveCurrentWeather(currentWeatherItem)
    }
}