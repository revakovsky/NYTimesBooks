package com.revakovsky.thenytimesbooks.di

import androidx.lifecycle.ViewModel
import com.revakovsky.thenytimesbooks.presentation.screens.books.BooksViewModel
import com.revakovsky.thenytimesbooks.presentation.screens.categories.CategoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(CategoryViewModel::class)
    @IntoMap
    abstract fun bindCategoryViewModel(categoryViewModel: CategoryViewModel): ViewModel

    @Binds
    @ClassKey(BooksViewModel::class)
    @IntoMap
    abstract fun bindBooksViewModel(booksViewModel: BooksViewModel): ViewModel

}