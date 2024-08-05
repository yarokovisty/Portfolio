package com.example.randomuser.data.network.dto

import com.example.randomuser.data.network.dto.userDTO.UserDTO
import com.google.gson.annotations.SerializedName

data class ResultDTO(
    @SerializedName("results")
    val results: List<UserDTO>
)