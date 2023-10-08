package com.revakovsky.thenytimesbooks.di

import android.app.Application
import com.revakovsky.data.di.DataDependenciesProvider
import com.revakovsky.thenytimesbooks.MainActivity
import com.revakovsky.thenytimesbooks.core.BaseViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [DataDependenciesProvider::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {

    fun viewModelsFactory(): BaseViewModelFactory
    fun inject(mainActivity: MainActivity)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun dataDependenciesProvider(dataDependenciesProvider: DataDependenciesProvider): Builder
        fun create(): AppComponent
    }

}
