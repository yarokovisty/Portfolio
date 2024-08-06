package com.example.randomuser.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.randomuser.R
import com.example.randomuser.databinding.ActivityMainBinding
import com.example.randomuser.presentation.RandomUserApplication
import com.example.randomuser.presentation.state.UserListState
import com.example.randomuser.presentation.viewmodel.UserListViewModel
import com.example.randomuser.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UserListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initComponent()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loadUserListFromNetwork()

        observerViewModel()
    }

    private fun initComponent() {
        val component = (application as RandomUserApplication).appComponent
        component.inject(this)
    }

    private fun observerViewModel() {
        viewModel.state.observe(this, ::renderState)
    }

    private fun renderState(state: UserListState) {
        when(state) {
            UserListState.Initial -> {}
            UserListState.Loading -> {}
            is UserListState.Success -> {Log.i("MyLog", state.userList.toString())}
            is UserListState.Error -> {Log.e("MyLog", state.errorMessage)}
        }
    }
}