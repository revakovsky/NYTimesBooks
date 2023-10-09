package com.revakovsky.thenytimesbooks.presentation.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.core.TheInternetNotification
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.LoadingProgressDialog
import com.revakovsky.thenytimesbooks.presentation.widgets.ToolBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    openBooksScreen: (String) -> Unit,
    viewModel: CategoryViewModel,
) {

    val categories by viewModel.categories.collectAsStateWithLifecycle(emptyList())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(false)
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle("")
    val hasConnectivity by viewModel.hasConnectivity.collectAsStateWithLifecycle(true)
    val connectedToTheInternet by viewModel.connectedToTheInternet.collectAsState(true)

    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    val swipeRefreshState = rememberSwipeRefreshState(isLoading)


    if (isLoading) LoadingProgressDialog()

    LaunchedEffect(
        key1 = errorMessage,
        key2 = hasConnectivity,
        key3 = connectedToTheInternet
    ) {
        if (errorMessage.isNotEmpty()) snackBarHostState.showSnackbar(errorMessage)

        TheInternetNotification.showNotification(
            hasConnectivity,
            connectedToTheInternet,
            snackBarHostState,
            context
        )

        viewModel.resetStates()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            ToolBar(
                titleText = stringResource(id = R.string.app_name),
                showNavigationIcon = false,
                scrollBehavior,
            )
        }
    ) { paddingValues ->

        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            state = swipeRefreshState,
            onRefresh = { viewModel.getCategories(shouldUpdateBooksInfo = true) },
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                content = {
                    items(categories.size) { itemIndex ->
                        val category = categories[itemIndex]

                        CategoryItem(
                            category,
                            onCategoryClick = { categoryName -> openBooksScreen(categoryName) }
                        )

                        if (itemIndex < categories.size) Divider(
                            modifier = Modifier.padding(horizontal = dimens.medium)
                        )

                    }
                }
            )

        }

    }

}
