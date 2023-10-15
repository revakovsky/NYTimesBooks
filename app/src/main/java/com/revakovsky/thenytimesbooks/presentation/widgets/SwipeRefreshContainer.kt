package com.revakovsky.thenytimesbooks.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SwipeRefreshContainer(
    isLoading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {

    val swipeRefreshState = rememberSwipeRefreshState(isLoading)

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        state = swipeRefreshState,
        onRefresh = { onRefresh() },
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) {
        content()
    }

}