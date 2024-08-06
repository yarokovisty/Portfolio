package com.example.randomuser.data.repository

import com.example.randomuser.data.datasource.RemoteDataSource
import com.example.randomuser.data.mapper.UserMapper
import com.example.randomuser.domain.entity.Result
import com.example.randomuser.domain.entity.UserItem
import com.example.randomuser.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: UserMapper
) : UserRepository {

    override suspend fun getUserListFromNetwork(): Result<List<UserItem>> =
        when (val result = remoteDataSource.getUserList()) {
            is Result.Success -> {
                Result.Success(result.data.map { mapper.mapUserDTOtoUserItem(it) })
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }


    override suspend fun getUserListFromDB(): List<UserItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: String): UserItem {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(userItem: UserItem) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshUserList() {
        TODO("Not yet implemented")
    }


}