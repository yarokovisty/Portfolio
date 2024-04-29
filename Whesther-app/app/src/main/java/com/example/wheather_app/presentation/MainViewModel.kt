package com.example.wheather_app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheather_app.data.mappers.toCurrentWeatherItem
import com.example.wheather_app.data.retrofit.WeatherRepositoryImpl
import com.example.wheather_app.domain.entity.CurrentWeatherItem
import com.example.wheather_app.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = WeatherRepositoryImpl()

    private val getCurrentWeatherUseCase = GetCurrentWeatherUseCase(repository)

    private val _currentWeatherItem = MutableLiveData<CurrentWeatherItem>()
    val currentWeatherItem: LiveData<CurrentWeatherItem>
        get() = _currentWeatherItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getCurrentWeather(lat: Double, lon: Double) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                _currentWeatherItem.value = getCurrentWeatherUseCase(lat, lon).toCurrentWeatherItem()
                _isLoading.value = false
            } catch (ex: Exception) {
                _error.value = ex.message
            }
        }
    }

}