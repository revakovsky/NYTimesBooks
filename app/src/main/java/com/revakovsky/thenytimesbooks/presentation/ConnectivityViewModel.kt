package com.revakovsky.thenytimesbooks.presentation

import androidx.lifecycle.viewModelScope
import com.revakovsky.thenytimesbooks.core.BaseViewModel
import com.revakovsky.thenytimesbooks.core.ConnectivityObserver
import com.revakovsky.thenytimesbooks.core.InternetStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConnectivityViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver,
) : BaseViewModel() {

    private val _internetStatus = MutableStateFlow(InternetStatus.Status.Undefined)
    val internetStatus = _internetStatus.asStateFlow()

    init {
        checkConnectivity(connectivityObserver)
    }

    private fun checkConnectivity(connectivityObserver: ConnectivityObserver) {
        _internetStatus.value =
            if (connectivityObserver.hasConnection()) InternetStatus.Status.Available
            else InternetStatus.Status.Unavailable

        connectivityObserver.observeConnectivity().onEach { connectivityStatus ->
            when (connectivityStatus) {

                InternetStatus.Status.Available -> {
                    _internetStatus.value =
                        if (_internetStatus.value == InternetStatus.Status.Unavailable) {
                            InternetStatus.Status.Appeared
                        } else InternetStatus.Status.Available
                }

                else -> _internetStatus.value = InternetStatus.Status.Unavailable
            }
        }.launchIn(viewModelScope)
    }

}