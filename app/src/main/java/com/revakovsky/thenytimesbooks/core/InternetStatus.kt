package com.revakovsky.thenytimesbooks.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object InternetStatus {

    var current by mutableStateOf(Status.Undefined)
        private set

    var showMessage by mutableStateOf(false)
        private set


    fun setCurrentStatus(status: Status) {
        current = status
    }

    fun isOnline(): Boolean {
        return current == Status.Available || current == Status.Appeared
    }

    fun showOfflineMessage() {
        showMessage = true
    }

    fun resetState() {
        showMessage = false
    }

    enum class Status {
        Available, Unavailable, Losing, Lost, Undefined, Appeared
    }
}