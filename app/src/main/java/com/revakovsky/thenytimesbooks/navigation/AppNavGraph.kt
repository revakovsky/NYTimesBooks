package com.revakovsky.thenytimesbooks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.revakovsky.thenytimesbooks.presentation.screens.books.BooksScreen
import com.revakovsky.thenytimesbooks.presentation.screens.books.BooksViewModel
import com.revakovsky.thenytimesbooks.presentation.screens.categories.CategoriesScreen
import com.revakovsky.thenytimesbooks.presentation.screens.categories.CategoryViewModel
import com.revakovsky.thenytimesbooks.presentation.screens.store.StoreScreen

@Composable
fun AppNavGraph(
    categoryViewModel: CategoryViewModel,
    booksViewModel: BooksViewModel,
) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screens.CategoriesScreen.route) {

        composable(route = Screens.CategoriesScreen.route) {
            CategoriesScreen(
                openBooksScreen = { categoryName ->
                    navController.navigate(Screens.BooksScreen.passCategoryName(categoryName))
                },
                categoryViewModel
            )
        }

        composableWithAnimatedTransition(
            route = Screens.BooksScreen.route,
            arguments = listOf(navArgument(CATEGORY_NAME) { type = NavType.StringType })
        ) { _, navBackStackEntry ->

            BooksScreen(
                categoryName = navBackStackEntry.arguments?.getString(CATEGORY_NAME) ?: "",
                navigateToOtherScreen = { linkToTheStore ->
                    if (linkToTheStore.isNotEmpty()) {
                        navController.navigate(Screens.StoreScreen.passLinkToTheStore(linkToTheStore))
                    } else navController.popBackStack()
                },
                booksViewModel
            )
        }

        composableWithAnimatedTransition(
            route = Screens.StoreScreen.route,
            arguments = listOf(navArgument(LINK_TO_THE_STORE) { type = NavType.StringType })
        ) { _, navBackStackEntry ->

            StoreScreen(
                url = navBackStackEntry.arguments?.getString(LINK_TO_THE_STORE) ?: "",
                categoryViewModel
            )
        }

    }

}