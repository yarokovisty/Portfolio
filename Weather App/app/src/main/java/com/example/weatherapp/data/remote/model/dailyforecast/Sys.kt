package com.example.weatherapp.data.remote.model.dailyforecast

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)