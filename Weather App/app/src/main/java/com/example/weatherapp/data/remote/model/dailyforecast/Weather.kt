package com.example.weatherapp.data.remote.model.dailyforecast

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)