package com.example.forecastapp.data.network.dto.hourlyforecastdto

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h") val threeHour: Double
)
