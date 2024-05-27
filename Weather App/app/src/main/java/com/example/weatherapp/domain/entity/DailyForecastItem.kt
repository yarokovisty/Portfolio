package com.example.weatherapp.domain.entity

data class DailyForecastItem(
    val date: String,
    val temperature: Int,
    val typeWeatherId: Int
)
