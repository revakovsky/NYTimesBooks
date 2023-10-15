package com.revakovsky.thenytimesbooks.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovsky.domain.util.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseViewModel @Inject constructor() : ViewModel() {

    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _hasConnection = MutableStateFlow<Boolean?>(null)
    val hasConnection = _hasConnection.asStateFlow()


    protected fun checkConnectivity(connectivityObserver: ConnectivityObserver) {
        if (connectivityObserver.hasConnection()) _hasConnection.value = isInternetAvailable
        else {
            isInternetAvailable = false
            _hasConnection.value = isInternetAvailable
        }

        connectivityObserver.observeConnectivity().onEach { connectivityStatus ->
            when (connectivityStatus) {

                ConnectivityObserver.Status.Available -> {
                    if (isInternetAvailable == false) {
                        _hasConnection.value = true
                        isInternetAvailable = null
                    }
                }

                else -> {
                    isInternetAvailable = false
                    _hasConnection.value = isInternetAvailable
                }
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
        _hasConnection.value = null
        _errorMessage.value = ""
    }


    companion object {
        var isInternetAvailable: Boolean? = null
    }

}