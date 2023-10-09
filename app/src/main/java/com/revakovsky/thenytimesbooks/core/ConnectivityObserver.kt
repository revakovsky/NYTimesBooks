package com.revakovsky.thenytimesbooks.core

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun hasConnection(): Boolean
    fun observeConnectivity(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }

}