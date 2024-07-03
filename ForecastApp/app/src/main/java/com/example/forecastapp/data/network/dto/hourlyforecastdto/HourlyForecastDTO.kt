package com.example.forecastapp.data.network.dto.hourlyforecastdto

import com.google.gson.annotations.SerializedName

data class HourlyForecastDTO(
    @SerializedName("cod") val cod: String,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val list: List<WeatherData>,
    @SerializedName("city") val city: City
)