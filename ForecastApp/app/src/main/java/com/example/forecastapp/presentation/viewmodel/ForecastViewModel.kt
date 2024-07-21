package com.example.forecastapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.usecase.ClearDailyForecastUseCase
import com.example.forecastapp.domain.usecase.ClearHourlyForecastUseCase
import com.example.forecastapp.domain.usecase.GetCurrentWeatherUseCase
import com.example.forecastapp.domain.usecase.GetDailyForecastUseCase
import com.example.forecastapp.domain.usecase.GetHourlyForecastUseCase
import com.example.forecastapp.domain.usecase.SaveCurrentWeatherUseCase
import com.example.forecastapp.domain.usecase.SaveDailyForecastUseCase
import com.example.forecastapp.domain.usecase.SaveHourlyForecastUseCase
import com.example.forecastapp.presentation.state.ForecastState
import kotlinx.coroutines.async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val saveCurrentWeatherUseCase: SaveCurrentWeatherUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val saveHourlyForecastUseCase: SaveHourlyForecastUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val clearHourlyForecastUseCase: ClearHourlyForecastUseCase,
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    private val clearDailyForecastUseCase: ClearDailyForecastUseCase,
    private val saveDailyForecastUseCase: SaveDailyForecastUseCase
) : ViewModel() {

    private val _forecastState = MutableLiveData<ForecastState>()
    val forecastState: LiveData<ForecastState>
        get() = _forecastState

    var isReady: Boolean = false
        private set

    init {
        _forecastState.value = ForecastState.Initial
    }

    fun loadDataFromNetwork(lon: Double, lat: Double) {
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

                    saveLocal(
                        resultCurrentWeatherItem.data,
                        resultListHourlyForecastItem.data,
                        resultListDailyForecastItem.data
                    )
                }

                resultCurrentWeatherItem is Result.Error
                        || resultListHourlyForecastItem is Result.Error
                        || resultListDailyForecastItem is Result.Error
                -> {
                    _forecastState.value = ForecastState.Error("Error with Internet")
                }
            }
        }

    }

    fun loadDataFromDb() {
        _forecastState.value = ForecastState.Loading

        viewModelScope.launch {
            try {
                val currentWeatherItem = getCurrentWeatherUseCase()
                val hourlyForecast = getHourlyForecastUseCase()
                val dailyForecast = getDailyForecastUseCase()

                if (currentWeatherItem != null) {
                    _forecastState.value = ForecastState.Success(
                        currentWeatherItem,
                        hourlyForecast,
                        dailyForecast
                    )
                } else {
                    _forecastState.value = ForecastState.Error("Error with Internet")
                }

                isReady = true

            } catch (ex: Exception) {
                _forecastState.value = ForecastState.Error("Error with Internet")
            }


        }

    }

    private fun saveLocal(
        currentWeatherItem: CurrentWeatherItem,
        hourlyForecast: List<HourlyForecastItem>,
        dailyForecast: List<DailyForecastItem>
    ) {
        viewModelScope.launch {
            saveCurrentWeatherUseCase(currentWeatherItem)
            clearHourlyForecastUseCase()
            saveHourlyForecastUseCase(hourlyForecast)
            clearDailyForecastUseCase()
            saveDailyForecastUseCase(dailyForecast)
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