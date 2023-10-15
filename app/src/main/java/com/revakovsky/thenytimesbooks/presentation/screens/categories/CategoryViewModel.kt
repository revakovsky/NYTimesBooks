package com.revakovsky.thenytimesbooks.presentation.screens.categories

import androidx.lifecycle.viewModelScope
import com.revakovsky.domain.useCase.GetBookCategoriesUseCase
import com.revakovsky.thenytimesbooks.core.BaseViewModel
import com.revakovsky.thenytimesbooks.core.ConnectivityObserver
import com.revakovsky.thenytimesbooks.presentation.models.CategoryUi
import com.revakovsky.thenytimesbooks.utils.mapToCategoryUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver,
    private val getBookCategoriesUseCase: GetBookCategoriesUseCase,
) : BaseViewModel() {

    private val _categories = MutableStateFlow<List<CategoryUi>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        checkConnectivity(connectivityObserver)
        getCategories(shouldUpdateCategories = false)
    }

    fun getCategories(shouldUpdateCategories: Boolean) {
        _isLoading.tryEmit(true)
        viewModelScope.launch(Dispatchers.IO) {
            val dataResult = getBookCategoriesUseCase(shouldUpdateCategories)
            processDataResult(
                dataResult,
                _categories,
                transformDataTypeToUi = { category -> category.mapToCategoryUi() }
            )
        }
    }

}