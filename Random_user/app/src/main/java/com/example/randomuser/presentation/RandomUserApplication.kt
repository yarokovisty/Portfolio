package com.example.randomuser.presentation

import android.app.Application
import com.example.randomuser.di.component.DaggerAppComponent

class RandomUserApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}