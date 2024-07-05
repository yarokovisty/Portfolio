package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class SaveHourlyForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    suspend operator fun invoke(listHourlyForecastItem: List<HourlyForecastItem>) {
        repository.saveHourlyForecast(listHourlyForecastItem)
    }
}