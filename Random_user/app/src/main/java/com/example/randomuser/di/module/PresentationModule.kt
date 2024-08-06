package com.example.randomuser.di.module

import androidx.lifecycle.ViewModel
import com.example.randomuser.di.key.ViewModelKey
import com.example.randomuser.presentation.viewmodel.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    @Binds
    fun bindUserListViewModel(impl: UserListViewModel): ViewModel
}