package com.example.forecastapp.data.datasource

import com.example.forecastapp.data.network.dto.currentweatherdto.CurrentWeatherDTO
import com.example.forecastapp.data.network.dto.hourlyforecastdto.HourlyForecastDTO
import com.example.forecastapp.domain.entity.Result

interface RemoteForecastDataSource {

    suspend fun getCurrentWeather(lon: Double, lat: Double): Result<CurrentWeatherDTO>

    suspend fun getHourlyForecast(lon: Double, lat: Double): Result<HourlyForecastDTO>
}