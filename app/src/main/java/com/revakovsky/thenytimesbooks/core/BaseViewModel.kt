package com.revakovsky.thenytimesbooks.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {

    protected val _hasInternetConnection = MutableStateFlow<Boolean?>(null)
    val hasInternetConnection = _hasInternetConnection.asStateFlow()

    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()


    open fun resetStates() {
        _hasInternetConnection.value = null
        _errorMessage.value = ""
    }

}