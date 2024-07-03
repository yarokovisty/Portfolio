package com.example.forecastapp.data.network.dto.currentweatherdto

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val `1h`: Double
)