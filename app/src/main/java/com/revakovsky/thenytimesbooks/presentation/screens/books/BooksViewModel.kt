package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.lifecycle.viewModelScope
import com.revakovsky.domain.useCase.GetBookStoresUseCase
import com.revakovsky.domain.useCase.GetBooksUseCase
import com.revakovsky.thenytimesbooks.core.BaseViewModel
import com.revakovsky.thenytimesbooks.core.ConnectivityObserver
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.presentation.models.StoreUi
import com.revakovsky.thenytimesbooks.utils.mapToBookUi
import com.revakovsky.thenytimesbooks.utils.mapToStoreUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BooksViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
    private val getBooksUseCase: GetBooksUseCase,
    private val getBookStoresUseCase: GetBookStoresUseCase,
) : BaseViewModel() {

    private val _books = MutableStateFlow<List<BookUi>>(emptyList())
    val books = _books.asStateFlow()

    private val _stores = MutableStateFlow<List<StoreUi>>(emptyList())
    val stores = _stores.asStateFlow()

    init {
        checkTheInternet()
    }

    fun checkTheInternet() {
        checkConnectivity(connectivityObserver)
    }

    fun getBooksFromCategory(categoryName: String, shouldUpdateBooksInfo: Boolean) {
        if (shouldUpdateBooksInfo) _isLoading.tryEmit(true)
        viewModelScope.launch(Dispatchers.IO) {
            val dataResult = getBooksUseCase(categoryName, shouldUpdateBooksInfo)
            processDataResult(
                dataResult,
                _books,
                transformDataTypeToUi = { book -> book.mapToBookUi() }
            )
        }
    }

    fun getStoresByTheBookTitle(bookTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dataResult = getBookStoresUseCase(bookTitle)
            processDataResult(
                dataResult,
                _stores,
                transformDataTypeToUi = { store -> store.mapToStoreUi() }
            )
        }
    }

    fun refreshStoresList() {
        _stores.value = emptyList()
    }

}