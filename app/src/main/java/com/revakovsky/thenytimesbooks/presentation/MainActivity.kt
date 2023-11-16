package com.revakovsky.thenytimesbooks.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.appComponent
import com.revakovsky.thenytimesbooks.core.InternetStatus
import com.revakovsky.thenytimesbooks.core.daggerViewModel
import com.revakovsky.thenytimesbooks.navigation.AppNavGraph
import com.revakovsky.thenytimesbooks.presentation.ui.theme.TheNYTimesBooksTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this@MainActivity)
        super.onCreate(savedInstanceState)

        setContent {
            TheNYTimesBooksTheme {
                val snackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackBarHostState) },
                ) { paddingValues ->

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        InitTheInternetObserver(snackBarHostState)

                        AppNavGraph()

                    }

                }

            }
        }

    }

    @Composable
    private fun InitTheInternetObserver(snackBarHostState: SnackbarHostState) {
        val viewModel: ConnectivityViewModel = daggerViewModel {
            appComponent.getConnectivityViewModel()
        }
        val internetStatus by viewModel.internetStatus.collectAsStateWithLifecycle()
        val showOfflineMessage = InternetStatus.showMessage

        LaunchedEffect(key1 = internetStatus) {
            InternetStatus.setCurrentStatus(internetStatus)

            if (internetStatus == InternetStatus.Status.Unavailable) {
                snackBarHostState.showSnackbar(getString(R.string.your_device_is_offline))
            }

            if (internetStatus == InternetStatus.Status.Appeared) {
                snackBarHostState.showSnackbar(getString(R.string.you_are_online_again))
            }
        }

        LaunchedEffect(key1 = showOfflineMessage) {
            if (showOfflineMessage) {
                snackBarHostState.showSnackbar(getString(R.string.your_device_is_offline))
                InternetStatus.resetState()
            }
        }
    }

}