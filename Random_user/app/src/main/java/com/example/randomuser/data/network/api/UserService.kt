package com.example.randomuser.data.network.api

import com.example.randomuser.data.network.dto.ResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("api/?results")
    suspend fun getUserList(
        @Query("results") result: Int = COUNT_USER
    ): ResultDTO

    companion object {
        private const val COUNT_USER = 100
    }
}