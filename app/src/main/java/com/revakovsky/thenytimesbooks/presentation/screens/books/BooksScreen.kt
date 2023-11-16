package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovsky.thenytimesbooks.core.InternetStatus
import com.revakovsky.thenytimesbooks.presentation.screens.books.storesDialog.ShowStoresDialog
import com.revakovsky.thenytimesbooks.presentation.widgets.ToolBar
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BooksScreen(
    categoryName: String,
    navigateToStore: (linkToTheStore: String) -> Unit,
    viewModel: BooksViewModel,
) {

    val books by viewModel.books.collectAsStateWithLifecycle()
    val stores by viewModel.stores.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val internetStatus = InternetStatus.current

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    var showTheStoresDialog by remember { mutableStateOf(false) }

    var shouldRefreshBookImage by remember { mutableStateOf(false) }
    var chosenBookTitle by remember { mutableStateOf("") }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            if (InternetStatus.isOnline()) {
                viewModel.apply {
                    refreshStoresList()
                    getBooksFromCategory(categoryName, shouldUpdateBooksInfo = true)
                    shouldRefreshBookImage = true
                }
            } else InternetStatus.showOfflineMessage()
        },
    )

    LaunchedEffect(key1 = true, key2 = stores) {
        viewModel.getBooksFromCategory(categoryName, shouldUpdateBooksInfo = false)
        if (stores.isNotEmpty()) showTheStoresDialog = true
    }

    LaunchedEffect(
        key1 = errorMessage,
        key2 = internetStatus,
        key3 = chosenBookTitle
    ) {
        if (errorMessage.isNotEmpty()) snackBarHostState.showSnackbar(errorMessage)

        if (internetStatus == InternetStatus.Status.Unavailable) chosenBookTitle = ""
        if (internetStatus == InternetStatus.Status.Appeared) {
            viewModel.getBooksFromCategory(categoryName, shouldUpdateBooksInfo = false)
            shouldRefreshBookImage = true
        }

        if (chosenBookTitle.isNotEmpty()) viewModel.getStoresByTheBookTitle(chosenBookTitle)

        viewModel.resetState()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            ToolBar(
                titleText = categoryName,
                scrollBehavior = scrollBehavior,
                onNavigationIconClick = { navigateToStore("") }
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
                    items(books.size) { itemIndex ->
                        val book = books[itemIndex]

                        BookItem(
                            book = book,
                            shouldRefreshImages = shouldRefreshBookImage,
                            showDivider = itemIndex != books.lastIndex,
                            onButtonBuyClick = { bookTitle ->
                                if (InternetStatus.isOnline()) chosenBookTitle = bookTitle
                                else InternetStatus.showOfflineMessage()
                            }
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

    if (showTheStoresDialog) {

        ShowStoresDialog(
            stores = stores,
            openChosenStore = { url ->
                navigateToStore(URLEncoder.encode(url, StandardCharsets.UTF_8.toString()))
            },
            onDismiss = {
                showTheStoresDialog = false
                chosenBookTitle = ""
                viewModel.refreshStoresList()
            }
        )

    }

}
