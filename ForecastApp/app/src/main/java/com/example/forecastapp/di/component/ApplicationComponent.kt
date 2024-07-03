package com.example.forecastapp.di.component

import android.app.Application
import com.example.forecastapp.di.module.DataModule
import com.example.forecastapp.di.module.DomainModule
import com.example.forecastapp.di.module.PresentationModule
import com.example.forecastapp.di.scope.AppScope
import com.example.forecastapp.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}