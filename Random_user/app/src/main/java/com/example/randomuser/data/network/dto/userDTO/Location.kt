package com.example.randomuser.data.network.dto.userDTO

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val city: String,
    @SerializedName("coordinates")
    val coordinates: Coordinates,
    @SerializedName("country")
    val country: String,
    @SerializedName("postcode")
    val postcode: Int,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: Street,
    @SerializedName("timezone")
    val timezone: Timezone
)