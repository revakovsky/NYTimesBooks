package com.revakovsky.thenytimesbooks.di

import androidx.lifecycle.ViewModel
import com.revakovsky.thenytimesbooks.presentation.screens.BooksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(BooksViewModel::class)
    @IntoMap
    abstract fun bindBooksViewModel(booksViewModel: BooksViewModel): ViewModel

}