package com.example.randomuser.data.network.dto.userDTO

import com.google.gson.annotations.SerializedName

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)