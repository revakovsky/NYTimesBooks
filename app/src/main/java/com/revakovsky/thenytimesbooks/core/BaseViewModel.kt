package com.revakovsky.thenytimesbooks.core

import androidx.lifecycle.ViewModel
import com.revakovsky.domain.util.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    protected fun <T, R> processDataResult(
        dataResult: DataResult<List<T>>,
        dataEmitter: MutableStateFlow<List<R>>,
        transformDataTypeToUi: (T) -> R,
    ) {
        when (dataResult) {

            is DataResult.Success -> {
                val data = dataResult.data?.map { transformDataTypeToUi(it) } ?: emptyList()
                dataEmitter.tryEmit(data)
                _isLoading.tryEmit(false)
            }

            is DataResult.Error -> {
                val errorMessage = dataResult.message.toString()
                _errorMessage.tryEmit(errorMessage)
                _isLoading.tryEmit(false)
            }

            else -> Unit
        }
    }

    open fun resetState() {
        _errorMessage.value = ""
    }

}