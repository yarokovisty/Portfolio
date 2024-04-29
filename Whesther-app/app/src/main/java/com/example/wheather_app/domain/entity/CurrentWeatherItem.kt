package com.example.wheather_app.domain.entity

data class CurrentWeatherItem(
    val description: String,
    val temp: Double,
    val humidity: Int,
    val wind: Double,
    val visibility: Int
)