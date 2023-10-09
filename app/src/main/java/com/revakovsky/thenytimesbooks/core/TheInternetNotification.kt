package com.revakovsky.thenytimesbooks.core

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.revakovsky.thenytimesbooks.R

object TheInternetNotification {

    suspend fun showNotification(
        hasConnectivity: Boolean?,
        connectedToTheInternet: Boolean?,
        snackBarHostState: SnackbarHostState,
        context: Context,
    ) {
        if (hasConnectivity == false || connectedToTheInternet == false) snackBarHostState.showSnackbar(
            context.getString(R.string.your_device_is_offline)
        )
        if (hasConnectivity == true) snackBarHostState.showSnackbar(
            context.getString(R.string.you_are_online_again)
        )
    }

}