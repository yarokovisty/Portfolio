package com.example.randomuser.data.datasource

import com.example.randomuser.data.network.api.UserService
import com.example.randomuser.data.network.dto.userDTO.UserDTO
import com.example.randomuser.domain.entity.Result
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : RemoteDataSource {

    override suspend fun getUserList(): Result<List<UserDTO>> =
        try {
            val userListDTO = userService.getUserList().results
            Result.Success(userListDTO)
        } catch (ex: Exception) {
            Result.Error(ex)
        }


}