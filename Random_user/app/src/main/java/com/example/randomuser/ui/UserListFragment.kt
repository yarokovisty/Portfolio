package com.example.randomuser.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.randomuser.databinding.FragmentUserListBinding
import com.example.randomuser.presentation.state.UserListState
import com.example.randomuser.presentation.viewmodel.UserListViewModel
import com.example.randomuser.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UserListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserListViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadUserList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadUserList() {
        viewModel.loadUserListFromNetwork()
    }

    private fun observerViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::renderState)
    }

    private fun renderState(state: UserListState) {
        when(state) {
            UserListState.Initial -> {}
            UserListState.Loading -> {}
            is UserListState.Success -> {
                Log.i("MyLog", state.userList.toString())}
            is UserListState.Error -> {
                Log.e("MyLog", state.errorMessage)}
        }
    }
}