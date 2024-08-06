package com.example.randomuser.di.component

import android.app.Application
import com.example.randomuser.di.module.DataModule
import com.example.randomuser.di.module.NetworkModule
import com.example.randomuser.di.scope.AppScope
import com.example.randomuser.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}