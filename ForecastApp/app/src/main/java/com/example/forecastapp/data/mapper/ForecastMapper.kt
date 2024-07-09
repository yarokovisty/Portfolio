package com.example.forecastapp.data.mapper

import com.example.forecastapp.data.database.model.DailyForecastDbModel
import com.example.forecastapp.data.database.model.HourlyForecastDbModel
import com.example.forecastapp.data.network.dto.currentweatherdto.CurrentWeatherDTO
import com.example.forecastapp.data.network.dto.hourlyforecastdto.HourlyForecastDTO
import com.example.forecastapp.data.network.dto.hourlyforecastdto.WeatherData
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.util.formatIsoDate
import com.example.forecastapp.util.formatUnixTimeHHMM
import javax.inject.Inject

class ForecastMapper @Inject constructor() {

    fun mapCurrentWeatherDTOtoCurrentWeatherItem(
        currentWeatherDTO: CurrentWeatherDTO
    ): CurrentWeatherItem =
        CurrentWeatherItem(
            id = currentWeatherDTO.weather[0].id,
            description = currentWeatherDTO.weather[0].description.uppercase(),
            temp = currentWeatherDTO.main.temp.toInt(),
            wind = currentWeatherDTO.wind.speed.toInt(),
            humidity = currentWeatherDTO.main.humidity,
            visibility = (currentWeatherDTO.visibility / KM).toInt()
        )

    fun mapHourlyForecastDTOtoHourlyForecastItem(
        listWeatherData: List<WeatherData>
    ): List<HourlyForecastItem> =
        listWeatherData.map {  weatherData ->
            HourlyForecastItem(
                id = weatherData.weather[0].id,
                time = formatUnixTimeHHMM(weatherData.dt),
                temp = weatherData.main.temp.toInt()
            )
        }

    fun mapHourlyForecastDTOtoDaileForecastItem(
        listWeatherData: List<WeatherData>
    ): List<DailyForecastItem> =
        listWeatherData.map { weatherData ->
            DailyForecastItem(
                id = weatherData.weather[0].id,
                date = formatIsoDate(weatherData.dt),
                temp = weatherData.main.temp.toInt()
            )
        }.groupBy { it.date }.map { (date, items) ->
            DailyForecastItem(
                items[0].id,
                date,
                items.map { it.temp }.average().toInt()
            )
        }

    fun mapHourlyForecastDbModelToHourlyForecastItem(
        hourlyForecastDbModel: HourlyForecastDbModel
    ): HourlyForecastItem =
        HourlyForecastItem(
            id = hourlyForecastDbModel.id,
            time = hourlyForecastDbModel.time,
            temp = hourlyForecastDbModel.temp
        )

    fun mapHourlyForecastItemToHourlyForecastDbModel(
        hourlyForecastItem: HourlyForecastItem
    ): HourlyForecastDbModel =
        HourlyForecastDbModel(
            id = hourlyForecastItem.id,
            time = hourlyForecastItem.time,
            temp = hourlyForecastItem.temp
        )

    fun mapDailyForecastItemToDailyForecastDbModel(
        dailyForecastItem: DailyForecastItem
    ): DailyForecastDbModel =
        DailyForecastDbModel(
            id = dailyForecastItem.id,
            date = dailyForecastItem.date,
            temp = dailyForecastItem.temp
        )

    fun mapDailyForecastDbModelToDailyForecastItem(
        dailyForecastDbModel: DailyForecastDbModel
    ): DailyForecastItem =
        DailyForecastItem(
            id = dailyForecastDbModel.id,
            date = dailyForecastDbModel.date,
            temp = dailyForecastDbModel.temp
        )


    companion object {
        private const val KM = 1000.0
    }
}