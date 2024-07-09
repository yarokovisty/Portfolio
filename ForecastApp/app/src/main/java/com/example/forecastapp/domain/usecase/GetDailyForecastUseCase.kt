package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class GetDailyForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    suspend operator fun invoke(lon: Double, lat: Double): Result<List<DailyForecastItem>> =
        repository.getDailyForecast(lon, lat)

    suspend operator fun invoke(): List<DailyForecastItem> =
        repository.getDailyForecast()
}