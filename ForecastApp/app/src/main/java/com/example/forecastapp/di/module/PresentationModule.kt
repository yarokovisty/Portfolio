package com.example.forecastapp.di.module

import androidx.lifecycle.ViewModel
import com.example.forecastapp.di.key.ViewModelKey
import com.example.forecastapp.presentation.viewmodel.ForecastViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    @Binds
    fun bindForecastViewModel(impl: ForecastViewModel): ViewModel
}