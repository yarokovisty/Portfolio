package com.example.randomuser.di.module

import com.example.randomuser.data.repository.UserRepositoryImpl
import com.example.randomuser.di.scope.AppScope
import com.example.randomuser.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @AppScope
    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}