package com.example.randomuser.data.network.dto.userDTO

import com.google.gson.annotations.SerializedName

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: Int,
    val state: String,
    val street: Street,
    val timezone: Timezone
)