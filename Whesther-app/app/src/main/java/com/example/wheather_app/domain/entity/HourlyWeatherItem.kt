package com.example.wheather_app.domain.entity

data class HourlyWeatherItem(
    val time: String,
    val type: Int,
    val temperature: Double
)
