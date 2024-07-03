package com.example.forecastapp.data.network.api

import com.example.forecastapp.data.network.dto.CurrentWeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {

    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String = LANG,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS
    ): CurrentWeatherDTO

    companion object {
        private const val LANG = "ru"
        private const val API_KEY = "bc509216035463e1774a8d5c0ae0b096"
        private const val UNITS = "metric"
    }
}