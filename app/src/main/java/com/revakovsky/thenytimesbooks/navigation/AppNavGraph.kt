package com.revakovsky.thenytimesbooks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.revakovsky.thenytimesbooks.presentation.screens.BooksViewModel
import com.revakovsky.thenytimesbooks.presentation.screens.books.BooksScreen
import com.revakovsky.thenytimesbooks.presentation.screens.categories.CategoriesScreen
import com.revakovsky.thenytimesbooks.presentation.screens.store.StoreScreen

@Composable
fun AppNavGraph(viewModel: BooksViewModel) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screens.CategoriesScreen.route) {

        composable(route = Screens.CategoriesScreen.route) {
            CategoriesScreen(
                openBooksScreen = { categoryName ->
                    navController.navigate(Screens.BooksScreen.arguments(categoryName))
                },
                viewModel
            )
        }

        composableWithAnimatedTransition(
            route = Screens.BooksScreen.route,
            arguments = listOf(navArgument(CATEGORY_NAME) { type = NavType.StringType })
        ) { _, navBackStackEntry ->

            BooksScreen(
                categoryName = navBackStackEntry.arguments?.getString(CATEGORY_NAME) ?: "",
                openStoresScreen = { linkToBook ->
                    navController.navigate(Screens.StoreScreen.arguments(linkToBook))
                },
                viewModel
            )
        }

        composableWithAnimatedTransition(
            route = Screens.StoreScreen.route,
            arguments = listOf(navArgument(LINK_TO_BOOK) { type = NavType.StringType })
        ) { _, navBackStackEntry ->

            StoreScreen(
                url = navBackStackEntry.arguments?.getString(LINK_TO_BOOK) ?: "",
                viewModel
            )
        }

    }

}