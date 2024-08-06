package com.example.randomuser.data.datasource

import com.example.randomuser.data.network.dto.userDTO.UserDTO
import com.example.randomuser.domain.entity.Result

interface RemoteDataSource {

    suspend fun getUserList(): Result<List<UserDTO>>
}