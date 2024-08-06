package com.example.randomuser.data.network.dto.userDTO

import com.google.gson.annotations.SerializedName

data class Name(
    val first: String,
    val last: String,
    val title: String
)