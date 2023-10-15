package com.revakovsky.thenytimesbooks.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppProviderModule {

    @Provides
    fun provideAppContext(application: Application): Context = application.applicationContext

}