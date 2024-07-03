package com.example.forecastapp.di.module

import com.example.forecastapp.data.repository.ForecastRepositoryImpl
import com.example.forecastapp.di.scope.AppScope
import com.example.forecastapp.domain.repository.ForecastRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @AppScope
    @Binds
    fun bindForecastRepository(impl: ForecastRepositoryImpl): ForecastRepository
}