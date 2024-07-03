package com.example.forecastapp.data.repository

import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.mapper.ForecastMapper
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val remoteForecastDataSource: RemoteForecastDataSource,
    private val mapper: ForecastMapper
) : ForecastRepository {

    override suspend fun getCurrentWeather(lon: Double, lat: Double): Result<CurrentWeatherItem> =
        when(val result = remoteForecastDataSource.getCurrentWeather(lon, lat)) {
            is Result.Success -> {
                Result.Success(mapper.mapCurrentWeatherDTOtoCurrentWeatherItem(result.data))
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }


}