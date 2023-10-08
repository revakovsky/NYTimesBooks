package com.revakovsky.data.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModuleProvider::class, DataModuleBinder::class])
interface DataComponent : DataDependenciesProvider {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun create(): DataComponent
    }

}
