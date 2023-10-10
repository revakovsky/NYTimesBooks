package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.lifecycle.viewModelScope
import com.revakovsky.domain.useCase.GetBooksUseCase
import com.revakovsky.thenytimesbooks.core.BaseViewModel
import com.revakovsky.thenytimesbooks.core.ConnectivityObserver
import com.revakovsky.thenytimesbooks.core.StringProvider
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.utils.mapToBookUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BooksViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver,
    stringProvider: StringProvider,
    private val getBooksUseCase: GetBooksUseCase,
) : BaseViewModel(connectivityObserver, stringProvider) {

    private val _books = MutableStateFlow<List<BookUi>>(emptyList())
    val books = _books.asStateFlow()

    init {
        checkConnectivity()
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

}