package com.example.forecastapp.data.network.dto.hourlyforecastdto

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod") val pod: String
)