package com.revakovsky.thenytimesbooks.presentation.screens

import com.revakovsky.domain.useCase.GetBookCategoriesUseCase
import com.revakovsky.domain.useCase.GetBookStoresUseCase
import com.revakovsky.domain.useCase.GetBooksUseCase
import com.revakovsky.thenytimesbooks.core.BaseViewModel
import javax.inject.Inject

class BooksViewModel @Inject constructor(
    private val getBookCategoriesUseCase: GetBookCategoriesUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val getBookStoresUseCase: GetBookStoresUseCase,
) : BaseViewModel() {

}