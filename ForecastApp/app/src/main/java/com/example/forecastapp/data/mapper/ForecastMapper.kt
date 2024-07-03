package com.example.forecastapp.data.mapper

import com.example.forecastapp.data.network.dto.currentweatherdto.CurrentWeatherDTO
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import javax.inject.Inject

class ForecastMapper @Inject constructor() {

    fun mapCurrentWeatherDTOtoCurrentWeatherItem(
        currentWeatherDTO: CurrentWeatherDTO
    ): CurrentWeatherItem =
        CurrentWeatherItem(
            id = currentWeatherDTO.weather[0].id,
            description = currentWeatherDTO.weather[0].description.uppercase(),
            temp = currentWeatherDTO.main.temp.toInt(),
            wind = currentWeatherDTO.wind.speed.toInt(),
            humidity = currentWeatherDTO.main.humidity,
            visibility = (currentWeatherDTO.visibility / KM).toInt()
        )

    companion object {
        private const val KM = 1000.0
    }
}