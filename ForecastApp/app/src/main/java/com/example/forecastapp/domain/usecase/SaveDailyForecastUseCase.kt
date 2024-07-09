package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class SaveDailyForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {
    suspend operator fun invoke(listDailyForecastItem: List<DailyForecastItem>) =
        repository.saveDailyForecast(listDailyForecastItem)
}