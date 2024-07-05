package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class GetHourlyForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    suspend operator fun invoke(): List<HourlyForecastItem> =
        repository.getHourlyForecast()

    suspend operator fun invoke(lon: Double, lat: Double): Result<List<HourlyForecastItem>> =
        repository.getHourlyForecast(lon, lat)
}