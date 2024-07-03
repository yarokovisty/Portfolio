package com.example.forecastapp.data.mapper

import com.example.forecastapp.data.network.dto.currentweatherdto.CurrentWeatherDTO
import com.example.forecastapp.data.network.dto.hourlyforecastdto.HourlyForecastDTO
import com.example.forecastapp.data.network.dto.hourlyforecastdto.WeatherData
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.util.formatUnixTime
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

    fun mapHourlyForecastDTOtoHourlyForecastItem(
        listWeatherData: List<WeatherData>
    ): List<HourlyForecastItem> =
        listWeatherData.map {  weatherData ->
            HourlyForecastItem(
                id = weatherData.weather[0].id,
                time = formatUnixTime(weatherData.dt),
                temp = weatherData.main.temp.toInt()
            )
        }

    companion object {
        private const val KM = 1000.0
    }
}