package com.example.forecastapp.presentation

import android.app.Application
import com.example.forecastapp.di.component.DaggerApplicationComponent

class ForecastApplication : Application() {

    val appComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}