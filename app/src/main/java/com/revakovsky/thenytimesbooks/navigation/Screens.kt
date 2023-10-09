package com.revakovsky.thenytimesbooks.navigation

import com.revakovsky.thenytimesbooks.navigation.Screens.StoreScreen.route

const val CATEGORY_NAME = "category"
const val LINK_TO_BOOK = "link"

sealed class Screens(
    val route: String,
    val arguments: (String) -> String = { "" },
) {

    object CategoriesScreen : Screens("categories_screen")

    object BooksScreen : Screens(
        route = "books_screen/{$CATEGORY_NAME}",
        arguments = { category -> route.replace(oldValue = "{$CATEGORY_NAME}", newValue = category) }
    )

    object StoreScreen : Screens(
        route = "store_screen/{$LINK_TO_BOOK}",
        arguments = { url -> route.replace(oldValue = "{$LINK_TO_BOOK}", newValue = url) }
    )

}
