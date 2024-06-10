package com.example.weatherapp.data.repository

import com.example.weatherapp.data.datasource.RemoteForecastDataSource
import com.example.weatherapp.data.mapper.ForecastMapper
import com.example.weatherapp.domain.entity.CurrentForecastItem
import com.example.weatherapp.domain.entity.DailyForecastItem
import com.example.weatherapp.domain.entity.HourlyForecastItem
import com.example.weatherapp.domain.repository.ForecastRepository

class RemoteForecastRepositoryImpl(
    private val remoteForecastDataSource: RemoteForecastDataSource,
    private val mapper: ForecastMapper
) : ForecastRepository {

    override suspend fun getCurrentForecast(lat: Double, lon: Double): CurrentForecastItem =
        mapper.mapDtoToEntity(remoteForecastDataSource.getCurrentForecast(lat, lon))

    override suspend fun getHourlyForecast(lat: Double, lon: Double): List<HourlyForecastItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyForecast(lat: Double, lon: Double): List<DailyForecastItem> {
        TODO("Not yet implemented")
    }
}