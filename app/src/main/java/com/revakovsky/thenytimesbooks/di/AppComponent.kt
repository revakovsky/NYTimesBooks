package com.revakovsky.thenytimesbooks.di

import android.app.Application
import com.revakovsky.thenytimesbooks.MainActivity
import com.revakovsky.thenytimesbooks.core.BaseViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun viewModelsFactory(): BaseViewModelFactory
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun create(): AppComponent
    }

}