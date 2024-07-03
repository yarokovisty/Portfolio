package com.example.forecastapp.ui

import android.app.Application
import com.example.forecastapp.di.component.DaggerApplicationComponent

class ForecastApplication : Application() {

    val appComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}