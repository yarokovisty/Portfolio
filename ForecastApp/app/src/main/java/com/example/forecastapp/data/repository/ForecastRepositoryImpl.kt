package com.example.forecastapp.data.repository

import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.mapper.ForecastMapper
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val remoteForecastDataSource: RemoteForecastDataSource,
    private val mapper: ForecastMapper
) : ForecastRepository {

    override suspend fun getCurrentWeather(lon: Double, lat: Double): Result<CurrentWeatherItem> =
        when (val result = remoteForecastDataSource.getCurrentWeather(lon, lat)) {
            is Result.Success -> {
                Result.Success(mapper.mapCurrentWeatherDTOtoCurrentWeatherItem(result.data))
            }

            is Result.Error -> {
                Result.Error(result.exception)
            }
        }

    override suspend fun getHourlyForecast(
        lon: Double,
        lat: Double
    ): Result<List<HourlyForecastItem>> =
        when (val result = remoteForecastDataSource.getHourlyForecast(lon, lat)) {
            is Result.Success -> {
                Result.Success(
                    mapper.mapHourlyForecastDTOtoHourlyForecastItem(
                        result.data.list
                    )
                )
            }

            is Result.Error -> {
                Result.Error(result.exception)
            }
        }

    override suspend fun getDailyForecast(
        lon: Double,
        lat: Double
    ): Result<List<DailyForecastItem>> =
        when (val result = remoteForecastDataSource.getHourlyForecast(lon, lat)) {
            is Result.Success -> {
                val listHourlyForecastDTO = result.data.list

                Result.Success(
                    mapper.mapHourlyForecastDTOtoDaileForecastItem(listHourlyForecastDTO)
                )
            }

            is Result.Error -> {
                Result.Error(result.exception)
            }
        }


}