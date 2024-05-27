package com.example.weatherapp.domain.entity

data class HourlyForecastItem(
    val time: String,
    val typeWeatherId: Int,
    val temperature: Int
)