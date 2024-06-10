package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.remote.model.currentforecast.CurrentForecastDto
import com.example.weatherapp.domain.entity.CurrentForecastItem

class ForecastMapper {

    fun mapDtoToEntity(currentForecastDto: CurrentForecastDto) = CurrentForecastItem(
        typeWeatherId = currentForecastDto.weather[0].id,
        temperature = currentForecastDto.main.temp.toInt(),
        windSpeed = currentForecastDto.wind.speed.toInt(),
        humidity = currentForecastDto.main.humidity,
        visibility = currentForecastDto.visibility / METERS_TO_KM
    )

    companion object {
        private const val METERS_TO_KM = 1000
    }
}