package com.revakovsky.thenytimesbooks.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class BaseViewModel(
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    protected val _connectedToTheInternet = MutableStateFlow<Boolean?>(null)
    val connectedToTheInternet = _connectedToTheInternet.asStateFlow()

    protected val _hasConnectivity = MutableStateFlow<Boolean?>(null)
    val hasConnectivity = _hasConnectivity.asStateFlow()

    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()


    protected fun checkConnectivity() {
        if (!connectivityObserver.hasConnection()) _connectedToTheInternet.tryEmit(false)
        connectivityObserver.observeConnectivity().onEach { connectivityStatus ->
            when (connectivityStatus) {
                ConnectivityObserver.Status.Available -> _hasConnectivity.tryEmit(true)
                else -> _hasConnectivity.tryEmit(false)
            }
        }.launchIn(viewModelScope)
    }

    open fun resetStates() {
        _connectedToTheInternet.value = null
        _hasConnectivity.value = null
        _errorMessage.value = ""
    }

}