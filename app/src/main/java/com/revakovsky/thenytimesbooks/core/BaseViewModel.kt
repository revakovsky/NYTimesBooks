package com.revakovsky.thenytimesbooks.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {

    protected val _connectedToTheInternet = MutableStateFlow<Boolean?>(null)
    val connectedToTheInternet = _connectedToTheInternet.asStateFlow()

    protected val _hasConnectivity = MutableStateFlow<Boolean?>(null)
    val hasConnectivity = _hasConnectivity.asStateFlow()

    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()


    open fun resetStates() {
        _connectedToTheInternet.value = null
        _hasConnectivity.value = null
        _errorMessage.value = ""
    }

}