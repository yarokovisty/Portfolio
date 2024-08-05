package com.example.randomuser.di.component

import android.app.Application
import com.example.randomuser.di.module.NetworkModule
import com.example.randomuser.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}