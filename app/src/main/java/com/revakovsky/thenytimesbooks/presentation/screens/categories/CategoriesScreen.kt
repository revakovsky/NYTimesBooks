package com.revakovsky.thenytimesbooks.presentation.screens.categories

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.core.ConnectivityObserver
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.ToolBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CategoriesScreen(
    openBooksScreen: (categoryName: String) -> Unit,
    viewModel: CategoryViewModel,
) {
    val context = LocalContext.current

    val categories by viewModel.categories.collectAsStateWithLifecycle(emptyList())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsState("")
    val internetStatus by viewModel.internetStatus.collectAsState(null)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.getCategories(shouldUpdateCategories = true) },
    )

    LaunchedEffect(key1 = errorMessage, key2 = internetStatus) {
        if (errorMessage.isNotEmpty()) snackBarHostState.showSnackbar(errorMessage)

        if (internetStatus == ConnectivityObserver.Status.Unavailable) snackBarHostState.showSnackbar(
            context.getString(R.string.your_device_is_offline)
        )

        if (internetStatus == ConnectivityObserver.Status.Appeared) {
            snackBarHostState.showSnackbar(context.getString(R.string.you_are_online_again))
            viewModel.getCategories(shouldUpdateCategories = false)
        }

        viewModel.resetState()
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .pullRefresh(pullRefreshState)
                .padding(paddingValues)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
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

            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

        }

    }

    BackHandler { (context as Activity).finish() }

}
