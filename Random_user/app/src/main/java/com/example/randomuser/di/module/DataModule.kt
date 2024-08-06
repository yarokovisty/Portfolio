package com.example.randomuser.di.module

import com.example.randomuser.data.datasource.RemoteDataSource
import com.example.randomuser.data.datasource.RemoteDataSourceImpl
import com.example.randomuser.di.scope.AppScope
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindRemoteUserDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}