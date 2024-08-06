package com.example.randomuser.data.network.dto

import com.example.randomuser.data.network.dto.userDTO.UserDTO

data class ResultDTO(
    val results: List<UserDTO>
)