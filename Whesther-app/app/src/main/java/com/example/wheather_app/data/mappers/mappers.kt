package com.example.wheather_app.data.mappers

import com.example.wheather_app.data.retrofit.dto.CurrentWeatherDto
import com.example.wheather_app.domain.entity.CurrentWeatherItem

fun CurrentWeatherDto.toCurrentWeatherItem(): CurrentWeatherItem {
    return CurrentWeatherItem(
        main = weather[0].id,
        description = weather[0].description,
        temp = main.temp,
        humidity = main.humidity,
        wind = wind.speed,
        visibility = visibility
    )
}