package com.example.wheather_app.data.retrofit.dto.hourly_forecast

data class HourlyWeatherDto(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<HourlyWeather>,
    val message: Int
)