package com.example.forecastapp.data.datasource

import com.example.forecastapp.domain.entity.CurrentWeatherItem

interface LocalForecastDataSource {

    fun saveCurrentWeather(currentWeatherItem: CurrentWeatherItem)

    fun getCurrentWeather(): CurrentWeatherItem?
}