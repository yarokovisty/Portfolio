package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.entity.Result
import com.example.randomuser.domain.entity.UserItem
import com.example.randomuser.domain.repository.UserRepository
import javax.inject.Inject

class GetUserListFromNetworkUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(): Result<List<UserItem>> = repository.getUserListFromNetwork()


}