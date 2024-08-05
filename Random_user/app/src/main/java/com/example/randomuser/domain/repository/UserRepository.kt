package com.example.randomuser.domain.repository

import com.example.randomuser.domain.entity.Result
import com.example.randomuser.domain.entity.UserItem

interface UserRepository {

    suspend fun getUserListFromNetwork(): Result<List<UserItem>>

    suspend fun getUserListFromDB(): List<UserItem>

    suspend fun getUser(userId: String): UserItem

    suspend fun addUser(userItem: UserItem)

    suspend fun refreshUserList()
}