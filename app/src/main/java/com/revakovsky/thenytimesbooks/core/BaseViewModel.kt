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

    private val _internetStatus = MutableStateFlow(ConnectivityObserver.Status.Undefined)
    val internetStatus = _internetStatus.asStateFlow()


    protected fun checkConnectivity(connectivityObserver: ConnectivityObserver) {
        status = if (connectivityObserver.hasConnection()) ConnectivityObserver.Status.Available
        else ConnectivityObserver.Status.Unavailable

        _internetStatus.value = status

        connectivityObserver.observeConnectivity().onEach { connectivityStatus ->
            when (connectivityStatus) {

                ConnectivityObserver.Status.Available -> {
                    if (status == ConnectivityObserver.Status.Unavailable) {
                        _internetStatus.value = ConnectivityObserver.Status.Appeared
                    } else {
                        _internetStatus.value = ConnectivityObserver.Status.Available
                    }
                    status = ConnectivityObserver.Status.Undefined
                }

                else -> {
                    status = ConnectivityObserver.Status.Unavailable
                    _internetStatus.value = status
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
        _internetStatus.value = ConnectivityObserver.Status.Undefined
        _errorMessage.value = ""
    }


    companion object {
        var status: ConnectivityObserver.Status = ConnectivityObserver.Status.Undefined
    }

}