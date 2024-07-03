package com.example.forecastapp.data.datasource

import com.example.forecastapp.data.network.api.ForecastService
import com.example.forecastapp.data.network.dto.CurrentWeatherDTO
import javax.inject.Inject

class RemoteForecastDataSourceImpl @Inject constructor(
    private val forecastService: ForecastService
) : RemoteForecastDataSource {

    override suspend fun getCurrentWeather(lon: Double, lat: Double): CurrentWeatherDTO =
        forecastService.getCurrentWeather(lat, lon)

}