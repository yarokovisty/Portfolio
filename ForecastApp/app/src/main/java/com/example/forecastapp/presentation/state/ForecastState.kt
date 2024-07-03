package com.example.forecastapp.presentation.state

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.HourlyForecastItem

sealed class ForecastState {

    data object Initial : ForecastState()

    data object Loading : ForecastState()

    data class Success(
        val currentWeatherItem: CurrentWeatherItem,
        val listHourlyForecastItem: List<HourlyForecastItem>
    ) : ForecastState()

    data class Error(val errorMessage: String) : ForecastState()
}