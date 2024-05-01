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
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val item = getCurrentWeatherUseCase(lat, lon)
                _currentWeatherItem.value = item
                _typeCurrentWeather.value = item.main
                _isLoading.value = false
            } catch (ex: Exception) {
                _error.value = ex.message
            }
        }
    }

    fun getHourlyWeather(lat: Double, lon: Double, currentTime: String) {


        viewModelScope.launch {
            try {
                val item = getHourlyWeatherUseCase(lat, lon)
                _hourlyWeatherList.value = sortHourlyForecast(item, currentTime)
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



}