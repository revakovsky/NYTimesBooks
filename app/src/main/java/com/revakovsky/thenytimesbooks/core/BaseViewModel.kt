package com.revakovsky.thenytimesbooks.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovsky.domain.util.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class BaseViewModel(
    private val connectivityObserver: ConnectivityObserver,
    private val stringProvider: StringProvider,
) : ViewModel() {

    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()


    protected fun checkConnectivity() {
        if (!connectivityObserver.hasConnection()) _errorMessage.value =
            stringProvider.provideOfflineText()
        connectivityObserver.observeConnectivity().onEach { connectivityStatus ->
            when (connectivityStatus) {
                ConnectivityObserver.Status.Available -> Unit
                else -> _errorMessage.value = stringProvider.provideOfflineText()
            }
        }.launchIn(viewModelScope)
    }

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
        }
    }

    open fun resetState() {
        _errorMessage.value = ""
    }

}