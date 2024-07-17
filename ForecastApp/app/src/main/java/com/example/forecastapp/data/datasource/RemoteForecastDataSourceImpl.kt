package com.example.forecastapp.data.datasource

import android.util.Log
import com.example.forecastapp.data.network.api.ForecastService
import com.example.forecastapp.data.network.dto.currentweatherdto.CurrentWeatherDTO
import com.example.forecastapp.data.network.dto.hourlyforecastdto.HourlyForecastDTO
import com.example.forecastapp.domain.entity.Result
import javax.inject.Inject

class RemoteForecastDataSourceImpl @Inject constructor(
    private val forecastService: ForecastService
) : RemoteForecastDataSource {

    override suspend fun getCurrentWeather(lon: Double, lat: Double): Result<CurrentWeatherDTO> =
        try {
            val currentWeatherDTO = forecastService.getCurrentWeather(lat, lon)
            Result.Success(currentWeatherDTO)
        } catch (ex: Exception) {
            Result.Error(ex)
        }

    override suspend fun getHourlyForecast(lon: Double, lat: Double): Result<HourlyForecastDTO> =
        try {
            val hourlyForecastDTO = forecastService.getHourlyForecast(lat, lon)
            Result.Success(hourlyForecastDTO)
        } catch (ex: Exception) {
            Result.Error(ex)
        }

    override suspend fun getDailyForecast(lon: Double, lat: Double): Result<HourlyForecastDTO> =
        try {
            val hourlyForecastDTO = forecastService.getHourlyForecast(lat, lon)
            Result.Success(hourlyForecastDTO)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
}