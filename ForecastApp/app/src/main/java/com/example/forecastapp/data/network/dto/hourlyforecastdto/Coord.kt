package com.example.forecastapp.data.network.dto.hourlyforecastdto

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)
