package com.example.randomuser.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.domain.entity.Result
import com.example.randomuser.domain.usecase.GetUserListFromNetworkUseCase
import com.example.randomuser.presentation.state.UserListState
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val getUserListFromNetworkUseCase: GetUserListFromNetworkUseCase
) : ViewModel() {

    private val _state = MutableLiveData<UserListState>()
    val state: LiveData<UserListState>
        get() = _state

    init {
        _state.value = UserListState.Initial
    }

    fun loadUserListFromNetwork() {
        _state.value = UserListState.Loading

        viewModelScope.launch {
            when(val result = getUserListFromNetworkUseCase()) {
                is Result.Success -> _state.value = UserListState.Success(result.data)
                is Result.Error -> _state.value = UserListState.Error("Error")
            }
        }
    }


}