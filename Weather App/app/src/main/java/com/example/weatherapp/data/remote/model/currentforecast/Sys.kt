package com.example.weatherapp.data.remote.model.currentforecast

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)