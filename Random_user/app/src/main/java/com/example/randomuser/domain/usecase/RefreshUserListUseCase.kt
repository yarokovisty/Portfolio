package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.repository.UserRepository
import javax.inject.Inject

class RefreshUserListUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke() = repository.refreshUserList()
}