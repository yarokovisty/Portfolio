package com.example.randomuser.presentation

import android.app.Application

class RandomUserApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory.create(this)
    }
}