package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.entity.UserItem
import com.example.randomuser.domain.repository.UserRepository
import javax.inject.Inject

class GetUserListFromDBUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(): List<UserItem> = repository.getUserListFromDB()
}