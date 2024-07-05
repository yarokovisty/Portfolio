package com.example.forecastapp.data.datasource

import com.example.forecastapp.data.sharedpreferences.SharedPreferencesHelper
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import javax.inject.Inject

class LocalForecastDataSourceImpl @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : LocalForecastDataSource {

    override fun saveCurrentWeather(currentWeatherItem: CurrentWeatherItem) {
        sharedPreferencesHelper.saveCurrentWeatherItem(currentWeatherItem)
    }

    override fun getCurrentWeather(): CurrentWeatherItem? =
        sharedPreferencesHelper.getCurrentWeatherItem()

}
