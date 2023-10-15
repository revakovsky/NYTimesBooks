package com.revakovsky.data.di

import com.revakovsky.data.BooksRepositoryImpl
import com.revakovsky.data.utils.ExceptionHandler
import com.revakovsky.data.utils.ExceptionHandlerImpl
import com.revakovsky.domain.repository.BooksRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class DataModuleBinder {

    @Binds
    @Singleton
    abstract fun bindBooksRepository(booksRepositoryImpl: BooksRepositoryImpl): BooksRepository

    @Binds
    @Singleton
    abstract fun bindExceptionHandler(exceptionHandlerImpl: ExceptionHandlerImpl): ExceptionHandler

}