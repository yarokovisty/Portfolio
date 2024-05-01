package com.example.wheather_app.data.mappers

import com.example.wheather_app.data.retrofit.dto.current_weather.CurrentWeatherDto
import com.example.wheather_app.data.retrofit.dto.hourly_forecast.HourlyWeather
import com.example.wheather_app.domain.entity.CurrentWeatherItem
import com.example.wheather_app.domain.entity.HourlyWeatherItem

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

fun HourlyWeather.toHourlyWeatherItem(): HourlyWeatherItem {
    return HourlyWeatherItem(
        time = dt_txt,
        type = weather[0].id,
        temperature = main.temp
    )
}

