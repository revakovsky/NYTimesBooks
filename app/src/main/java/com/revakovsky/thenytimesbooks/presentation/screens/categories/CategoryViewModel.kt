package com.revakovsky.thenytimesbooks.presentation.screens.categories

import androidx.lifecycle.viewModelScope
import com.revakovsky.domain.models.Category
import com.revakovsky.domain.useCase.GetBookCategoriesUseCase
import com.revakovsky.domain.util.DataResult
import com.revakovsky.thenytimesbooks.core.BaseViewModel
import com.revakovsky.thenytimesbooks.presentation.models.CategoryUi
import com.revakovsky.thenytimesbooks.utils.mapToCategoryUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val getBookCategoriesUseCase: GetBookCategoriesUseCase,
) : BaseViewModel() {

    private val _categories = MutableStateFlow<List<CategoryUi>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        getCategories(shouldUpdateBooksInfo = false)
    }

    fun getCategories(shouldUpdateBooksInfo: Boolean) {
        _isLoading.tryEmit(true)
        viewModelScope.launch(Dispatchers.IO) {
            val dataResult = getBookCategoriesUseCase(shouldUpdateBooksInfo)
            processDataResult(dataResult)
        }
    }

    private fun processDataResult(dataResult: DataResult<List<Category>>) {
        when (dataResult) {

            is DataResult.Success -> {
                val categories = dataResult.data?.map { it.mapToCategoryUi() } ?: emptyList()
                _categories.tryEmit(categories)
                _isLoading.tryEmit(false)
            }

            is DataResult.Error -> {
                val errorMessage = dataResult.message.toString()
                _errorMessage.tryEmit(errorMessage)
                _isLoading.tryEmit(false)
            }

        }
    }

}