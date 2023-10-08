package com.revakovsky.data.di

import com.revakovsky.domain.repository.BooksRepository

interface DataDependenciesProvider {

    val booksRepository: BooksRepository

}