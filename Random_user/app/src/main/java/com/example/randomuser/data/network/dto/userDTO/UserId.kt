package com.example.randomuser.data.network.dto.userDTO

import com.google.gson.annotations.SerializedName

data class UserId(
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)