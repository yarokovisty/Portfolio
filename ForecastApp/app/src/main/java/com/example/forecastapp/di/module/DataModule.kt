package com.example.forecastapp.di.module

import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.datasource.RemoteForecastDataSourceImpl
import com.example.forecastapp.data.network.api.ForecastService
import com.example.forecastapp.data.network.api.ServiceFactory
import com.example.forecastapp.di.scope.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindRemoteForecastDataSource(impl: RemoteForecastDataSourceImpl): RemoteForecastDataSource

    companion object {
        @Provides
        fun provideForecastService(): ForecastService =
            ServiceFactory.forecastService
    }
}