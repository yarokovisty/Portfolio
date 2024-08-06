package com.example.randomuser.presentation.state

import com.example.randomuser.domain.entity.UserItem

sealed class UserListState {

    data object Initial : UserListState()

    data object Loading : UserListState()

    data class Success(val userList: List<UserItem>)

    data class Error(val errorMessage: String)
}