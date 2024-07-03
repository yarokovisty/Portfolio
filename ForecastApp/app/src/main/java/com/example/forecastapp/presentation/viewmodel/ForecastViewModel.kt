package com.example.forecastapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.usecase.GetCurrentWeatherUseCase
import com.example.forecastapp.presentation.state.ForecastState
import kotlinx.coroutines.async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val _forecastState = MutableLiveData<ForecastState>()
    val forecastState: LiveData<ForecastState>
        get() = _forecastState

    init {
        _forecastState.value = ForecastState.Initial
    }

    fun loadData(lon: Double, lat: Double) {
        _forecastState.value = ForecastState.Loading

        viewModelScope.launch {
            val currentWeatherItemDeferred = async { getCurrentWeather(lon, lat) }

            when (val resultCurrentWeatherItem = currentWeatherItemDeferred.await()) {
                is Result.Success -> {
                    _forecastState.value = ForecastState.Success(resultCurrentWeatherItem.data)
                }

                is Result.Error -> {
                    _forecastState.value = ForecastState.Error("Error")
                }
            }
        }

    }

    private suspend fun getCurrentWeather(lon: Double, lat: Double) = with(Dispatchers.IO) {
        getCurrentWeatherUseCase(lon, lat)
    }

}