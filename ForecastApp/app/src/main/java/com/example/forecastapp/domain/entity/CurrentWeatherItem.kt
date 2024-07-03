package com.example.forecastapp.domain.entity

data class CurrentWeatherItem(
    val id: Int,
    val description: String,
    val temp: Int,
    val wind: Int,
    val humidity: Int,
    val visibility: Int
)
