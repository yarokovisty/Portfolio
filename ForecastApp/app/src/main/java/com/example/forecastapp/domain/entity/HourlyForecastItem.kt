package com.example.forecastapp.domain.entity

data class HourlyForecastItem(
    val id: Int,
    val time: String,
    val temp: Int
)