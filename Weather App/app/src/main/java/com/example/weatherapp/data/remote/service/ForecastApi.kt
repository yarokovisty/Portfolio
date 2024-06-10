package com.example.weatherapp.data.remote.service

import com.example.weatherapp.data.remote.model.currentforecast.CurrentForecastDto
import com.example.weatherapp.data.remote.model.dailyforecast.DailyForecastDto
import com.example.weatherapp.data.remote.model.hourlyforecast.HourlyForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("weather?")
    suspend fun getCurrentForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") lang: String = LANG
    ): CurrentForecastDto

    @GET("forecast?")
    suspend fun getHourlyForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") lang: String = LANG
    ): HourlyForecastDto

    @GET("forecast?")
    suspend fun getDailyForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") lang: String = LANG
    ): DailyForecastDto


    private companion object {
        const val API_KEY = "bc509216035463e1774a8d5c0ae0b096"
        const val UNITS = "metric"
        const val LANG = "ru"
    }
}