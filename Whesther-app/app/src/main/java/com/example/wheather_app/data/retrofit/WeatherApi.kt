package com.example.wheather_app.data.retrofit

import com.example.wheather_app.data.retrofit.dto.current_weather.CurrentWeatherDto
import com.example.wheather_app.data.retrofit.dto.hourly_forecast.HourlyWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather?")
    suspend fun getCurrentWeatherDto(
        @Query("lat") lat: Double = LAT_TOMSK,
        @Query("lon") lon: Double = LON_TOMSK,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") lang: String = LANG
    ) : CurrentWeatherDto

    @GET("forecast?")
    suspend fun getHourlyWeatherDto(
        @Query("lat") lat: Double = LAT_TOMSK,
        @Query("lon") lon: Double = LON_TOMSK,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") lang: String = LANG
    ) : HourlyWeatherDto

    private companion object {
        const val API_KEY = "bc509216035463e1774a8d5c0ae0b096"
        const val UNITS = "metric"
        const val LANG = "ru"
        const val LAT_TOMSK = 56.4611824
        const val LON_TOMSK = 84.96238
    }
}