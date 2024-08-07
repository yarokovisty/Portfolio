package com.example.forecastapp.di.module

import android.app.Application
import com.example.forecastapp.data.database.AppDatabase
import com.example.forecastapp.data.database.ForecastDao
import com.example.forecastapp.data.datasource.LocalForecastDataSource
import com.example.forecastapp.data.datasource.LocalForecastDataSourceImpl
import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.datasource.RemoteForecastDataSourceImpl
import com.example.forecastapp.data.sharedpreferences.SharedPreferencesHelper
import com.example.forecastapp.di.scope.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindRemoteForecastDataSource(impl: RemoteForecastDataSourceImpl): RemoteForecastDataSource

    @AppScope
    @Binds
    fun bindLocalForecastDataSource(impl: LocalForecastDataSourceImpl): LocalForecastDataSource

    companion object {

        @Provides
        fun provideSharedPreferencesHelper(
            application: Application
        ): SharedPreferencesHelper {
            return SharedPreferencesHelper(application)
        }

        @AppScope
        @Provides
        fun provideForecastDao(
            application: Application
        ): ForecastDao {
            return AppDatabase.getInstance(application).forecastDao()
        }

    }
}