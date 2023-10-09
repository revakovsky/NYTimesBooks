package com.revakovsky.thenytimesbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.revakovsky.thenytimesbooks.navigation.AppNavGraph
import com.revakovsky.thenytimesbooks.presentation.screens.BooksViewModel
import com.revakovsky.thenytimesbooks.presentation.ui.theme.TheNYTimesBooksTheme

class MainActivity : ComponentActivity() {

    private val booksViewModel by viewModels<BooksViewModel> { appComponent.viewModelsFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this@MainActivity)
        super.onCreate(savedInstanceState)

        setContent {
            TheNYTimesBooksTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(booksViewModel)
                }
            }
        }

    }

}