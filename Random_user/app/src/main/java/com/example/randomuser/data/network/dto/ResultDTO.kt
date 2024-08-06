package com.example.randomuser.data.network.dto

import com.example.randomuser.data.network.dto.userDTO.UserDTO
import com.google.gson.annotations.SerializedName

data class ResultDTO(
    val results: List<UserDTO>
)