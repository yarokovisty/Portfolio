package com.example.wheather_app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheather_app.data.mappers.toCurrentWeatherItem
import com.example.wheather_app.data.retrofit.WeatherRepositoryImpl
import com.example.wheather_app.domain.entity.CurrentWeatherItem
import com.example.wheather_app.domain.entity.HourlyWeatherItem
import com.example.wheather_app.domain.usecase.GetCurrentWeatherUseCase
import com.example.wheather_app.domain.usecase.GetHourlyWeatherUseCase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = WeatherRepositoryImpl()

    private val getCurrentWeatherUseCase = GetCurrentWeatherUseCase(repository)
    private val getHourlyWeatherUseCase = GetHourlyWeatherUseCase(repository)

    private val _currentWeatherItem = MutableLiveData<CurrentWeatherItem>()
    val currentWeatherItem: LiveData<CurrentWeatherItem>
        get() = _currentWeatherItem

    private val _hourlyWeatherList = MutableLiveData<List<HourlyWeatherItem>>()
    val hourlyWeatherList: LiveData<List<HourlyWeatherItem>>
        get() = _hourlyWeatherList

    private val _dailyForecastList = MutableLiveData<List<HourlyWeatherItem>>()
    val dailyForecastList: LiveData<List<HourlyWeatherItem>>
        get() = _dailyForecastList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _typeCurrentWeather = MutableLiveData<Int>()
    val typeCurrentWeather: LiveData<Int>
        get() = _typeCurrentWeather

    fun getCurrentWeather(lat: Double, lon: Double) {


        viewModelScope.launch {
            try {
                val item = getCurrentWeatherUseCase(lat, lon)
                _currentWeatherItem.value = item
                _typeCurrentWeather.value = item.main

            } catch (ex: Exception) {
                _error.value = ex.message
            }
        }
    }

    fun getHourlyWeather(lat: Double, lon: Double, currentTime: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val item = getHourlyWeatherUseCase(lat, lon)
                _hourlyWeatherList.value = sortHourlyForecast(item, currentTime)
                _dailyForecastList.value = sortDailyForecast(item)
                _isLoading.value = false
            } catch (ex: Exception) {
                _error.value = ex.message
            }
        }
    }

    private fun sortHourlyForecast(forecast: List<HourlyWeatherItem>, currentTime: String): List<HourlyWeatherItem> {
        val forecastSort = mutableListOf<HourlyWeatherItem>()

        for (i in forecast) {
            if (i.time > currentTime) {
                if (forecastSort.size < 5) forecastSort.add(i)
                else break
            }
        }

        return forecastSort
    }

    private fun sortDailyForecast(forecast: List<HourlyWeatherItem>): List<HourlyWeatherItem> {
        val forecastSort = mutableListOf<HourlyWeatherItem>()

        for (i in forecast) {
            val time = i.time.split(" ")[1].substringBefore(SEPARATOR_TIME)
            if (time == MIDDAY) {
                forecastSort.add(i)
            }

        }

        return forecastSort
    }

    companion object {
        private const val MIDDAY = "12"
        private const val SEPARATOR_TIME = ":"
    }



}