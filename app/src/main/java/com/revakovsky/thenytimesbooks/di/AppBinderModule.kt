package com.revakovsky.thenytimesbooks.di

import com.revakovsky.thenytimesbooks.core.ConnectivityObserver
import com.revakovsky.thenytimesbooks.core.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppBinderModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityObserver(
        networkConnectivityObserver: NetworkConnectivityObserver,
    ): ConnectivityObserver

}