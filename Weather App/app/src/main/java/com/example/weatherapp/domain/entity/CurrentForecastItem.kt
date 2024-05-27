package com.example.weatherapp.domain.entity

data class CurrentForecastItem(
    val typeWeatherId: Int,
    val temperature: Int,
    val windSpeed: Int,
    val humidity: Int,
    val visibility: Int
)
