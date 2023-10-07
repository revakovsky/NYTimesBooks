package com.revakovsky.thenytimesbooks.di

import androidx.lifecycle.ViewModel
import com.revakovsky.thenytimesbooks.presentation.screens.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(MainViewModel::class)
    @IntoMap
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

}