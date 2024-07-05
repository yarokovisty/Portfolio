package com.example.forecastapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.usecase.GetCurrentWeatherUseCase
import com.example.forecastapp.domain.usecase.GetDailyForecastUseCase
import com.example.forecastapp.domain.usecase.GetHourlyForecastUseCase
import com.example.forecastapp.presentation.state.ForecastState
import kotlinx.coroutines.async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val getDailyForecastUseCase: GetDailyForecastUseCase
) : ViewModel() {

    private val _forecastState = MutableLiveData<ForecastState>()
    val forecastState: LiveData<ForecastState>
        get() = _forecastState

    var isReady: Boolean = false
        private set

    init {
        _forecastState.value = ForecastState.Initial
    }

    fun loadData(lon: Double, lat: Double) {
        _forecastState.value = ForecastState.Loading

        viewModelScope.launch {
            val currentWeatherItemDeferred = async { getCurrentWeather(lon, lat) }
            val listHourlyForecastDeferred = async { getHourlyForecast(lon, lat) }
            val listDailyForecastDeferred = async { getDailyForecast(lon, lat) }

            val resultCurrentWeatherItem = currentWeatherItemDeferred.await()
            val resultListHourlyForecastItem = listHourlyForecastDeferred.await()
            val resultListDailyForecastItem = listDailyForecastDeferred.await()

            when {
                resultCurrentWeatherItem is Result.Success
                        && resultListHourlyForecastItem is Result.Success
                        && resultListDailyForecastItem is Result.Success -> {
                    _forecastState.value = ForecastState.Success(
                        resultCurrentWeatherItem.data,
                        resultListHourlyForecastItem.data,
                        resultListDailyForecastItem.data
                    )
                    isReady = true
                }

                resultCurrentWeatherItem is Result.Error
                        || resultListHourlyForecastItem is Result.Error -> {
                    _forecastState.value = ForecastState.Error("Error")
                }
            }
        }

    }

    private suspend fun getCurrentWeather(lon: Double, lat: Double) = with(Dispatchers.IO) {
        getCurrentWeatherUseCase(lon, lat)
    }

    private suspend fun getHourlyForecast(lon: Double, lat: Double) = with(Dispatchers.IO) {
        getHourlyForecastUseCase(lon, lat)
    }

    private suspend fun getDailyForecast(lon: Double, lat: Double) = with(Dispatchers.IO) {
        getDailyForecastUseCase(lon, lat)
    }

}