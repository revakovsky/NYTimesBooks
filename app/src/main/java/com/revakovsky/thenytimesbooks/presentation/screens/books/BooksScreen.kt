package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.core.ConnectivityObserver
import com.revakovsky.thenytimesbooks.core.WindowType
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.LoadingProgressDialog
import com.revakovsky.thenytimesbooks.presentation.widgets.SwipeRefreshContainer
import com.revakovsky.thenytimesbooks.presentation.widgets.ToolBar
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(
    categoryName: String,
    navigateToStore: (linkToTheStore: String) -> Unit,
    viewModel: BooksViewModel,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getBooksFromCategory(categoryName, shouldUpdateBooksInfo = false)
    }

    val books by viewModel.books.collectAsStateWithLifecycle(emptyList())
    val stores by viewModel.stores.collectAsStateWithLifecycle(emptyList())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(false)
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle("")
    val internetStatus by viewModel.internetStatus.collectAsState(null)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    val windowType = WindowType.getWindowType()
    var showTheStoresDialog by remember { mutableStateOf(false) }

    var shouldRefreshBookImages by remember { mutableStateOf(false) }
    var chosenBookTitle by remember { mutableStateOf("") }


    if (isLoading) LoadingProgressDialog()

    LaunchedEffect(
        key1 = errorMessage,
        key2 = internetStatus,
        key3 = chosenBookTitle
    ) {
        if (errorMessage.isNotEmpty()) snackBarHostState.showSnackbar(errorMessage)

        if (internetStatus == ConnectivityObserver.Status.Unavailable) {
            snackBarHostState.showSnackbar(context.getString(R.string.your_device_is_offline))
            chosenBookTitle = ""
        }

        if (internetStatus == ConnectivityObserver.Status.Appeared) {
            snackBarHostState.showSnackbar(context.getString(R.string.you_are_online_again))
            viewModel.getBooksFromCategory(categoryName, shouldUpdateBooksInfo = false)
            shouldRefreshBookImages = true
        }

        if (internetStatus == ConnectivityObserver.Status.Available && chosenBookTitle.isNotEmpty()) {
            viewModel.getStoresByTheBookTitle(chosenBookTitle)
        }

        viewModel.resetState()
    }

    LaunchedEffect(key1 = stores) {
        if (stores.isNotEmpty()) showTheStoresDialog = true
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

        SwipeRefreshContainer(
            isLoading = isLoading,
            onRefresh = {
                viewModel.apply {
                    refreshStoresList()
                    getBooksFromCategory(categoryName, shouldUpdateBooksInfo = true)
                }
                shouldRefreshBookImages = true
            }
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                content = {
                    items(books.size) { itemIndex ->
                        val book = books[itemIndex]

                        BookItem(
                            book = book,
                            windowType = windowType,
                            shouldRefreshImages = shouldRefreshBookImages,
                            onButtonBuyClick = { bookTitle ->
                                viewModel.apply {
                                    checkTheInternet()
                                    chosenBookTitle = bookTitle
                                }
                            }
                        )

                        if (itemIndex < books.size) Divider(
                            modifier = Modifier.padding(horizontal = dimens.medium)
                        )

                    }
                }
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
                viewModel.refreshStoresList()
            }
        )

    }

}
