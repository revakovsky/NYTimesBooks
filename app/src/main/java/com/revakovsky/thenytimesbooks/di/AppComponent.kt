package com.revakovsky.thenytimesbooks.di

import android.app.Application
import com.revakovsky.data.di.DataDependenciesProvider
import com.revakovsky.thenytimesbooks.presentation.ConnectivityViewModel
import com.revakovsky.thenytimesbooks.presentation.MainActivity
import com.revakovsky.thenytimesbooks.presentation.screens.books.BooksViewModel
import com.revakovsky.thenytimesbooks.presentation.screens.categories.CategoryViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [DataDependenciesProvider::class],
    modules = [AppProviderModule::class, ViewModelModule::class, AppBinderModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun getConnectivityViewModel(): ConnectivityViewModel
    fun getCategoryViewModel(): CategoryViewModel
    fun getBooksViewModel(): BooksViewModel


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun dataDependenciesProvider(dataDependenciesProvider: DataDependenciesProvider): Builder
        fun create(): AppComponent
    }

}
