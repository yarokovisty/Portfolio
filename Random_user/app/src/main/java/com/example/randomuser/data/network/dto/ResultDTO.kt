package com.example.randomuser.data.network.dto

import com.example.randomuser.data.network.dto.usetDTO.UserDTO
import com.google.gson.annotations.SerializedName

data class ResultDTO(
    @SerializedName("results")
    val results: List<UserDTO>
)