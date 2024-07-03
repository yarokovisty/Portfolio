package com.example.forecastapp.data.network.dto.currentweatherdto

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)