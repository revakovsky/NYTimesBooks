package com.revakovsky.thenytimesbooks

import android.app.Application
import android.content.Context
import com.revakovsky.data.di.DaggerDataComponent
import com.revakovsky.thenytimesbooks.di.AppComponent
import com.revakovsky.thenytimesbooks.di.DaggerAppComponent

class App : Application() {

    private lateinit var _appComponent: AppComponent
    val appComponent: AppComponent
        get() = _appComponent

    private val dataComponent by lazy {
        DaggerDataComponent
            .builder()
            .application(application = this@App)
            .create()
    }

    override fun onCreate() {
        super.onCreate()

        _appComponent = DaggerAppComponent
            .builder()
            .application(application = this@App)
            .dataDependenciesProvider(dataComponent)
            .create()
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> (applicationContext as App).appComponent
    }