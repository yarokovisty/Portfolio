package com.example.forecastapp.data.datasource

import com.example.forecastapp.data.database.ForecastDao
import com.example.forecastapp.data.database.model.HourlyForecastDbModel
import com.example.forecastapp.data.sharedpreferences.SharedPreferencesHelper
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import javax.inject.Inject

class LocalForecastDataSourceImpl @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val forecastDao: ForecastDao
) : LocalForecastDataSource {

    override fun saveCurrentWeather(currentWeatherItem: CurrentWeatherItem) {
        sharedPreferencesHelper.saveCurrentWeatherItem(currentWeatherItem)
    }

    override fun getCurrentWeather(): CurrentWeatherItem? =
        sharedPreferencesHelper.getCurrentWeatherItem()

    override suspend fun saveHourlyForecast(listHourlyForecastItem: List<HourlyForecastDbModel>) {
        forecastDao.saveHourlyForecast(listHourlyForecastItem)
    }

    override suspend fun getHourlyForecast(): List<HourlyForecastDbModel> =
        forecastDao.getHourlyForecast()

    override suspend fun clearHourlyForecast() {
        forecastDao.clearHourlyForecast()
    }

}
