package com.revakovsky.thenytimesbooks.presentation.screens.books

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovsky.thenytimesbooks.core.WindowType
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.LoadingProgressDialog
import com.revakovsky.thenytimesbooks.presentation.widgets.SwipeRefreshContainer
import com.revakovsky.thenytimesbooks.presentation.widgets.ToolBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(
    categoryName: String,
    navigateToOtherScreen: (linkToTheStore: String) -> Unit,
    viewModel: BooksViewModel,
) {

    LaunchedEffect(key1 = true) {
        viewModel.getBooksFromCategory(categoryName, shouldUpdateBooksInfo = false)
    }

    val books by viewModel.books.collectAsStateWithLifecycle(emptyList())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(false)
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle("")

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    val windowType = WindowType.getWindowType()

    var shouldRefreshBookImages by remember { mutableStateOf(false) }


    if (isLoading) LoadingProgressDialog()

    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage.isNotEmpty()) snackBarHostState.showSnackbar(errorMessage)
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
                onNavigationIconClick = { navigateToOtherScreen("") }
            )
        }
    ) { paddingValues ->

        SwipeRefreshContainer(
            isLoading = isLoading,
            onRefresh = {
                viewModel.getBooksFromCategory(
                    categoryName,
                    shouldUpdateBooksInfo = true
                )
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
                            onBookItemClick = { bookTitle ->

                                Log.d("TAG_Max", "BooksScreen.kt: bookTitle = $bookTitle")
                                Log.d("TAG_Max", "")

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

}
