package com.example.wheather_app.data.retrofit.dto.current_weather

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)