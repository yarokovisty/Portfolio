package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class ClearDailyForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {
    suspend operator fun invoke() {
        repository.clearDailyForecast()
    }
}