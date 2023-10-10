package com.revakovsky.thenytimesbooks.navigation

const val CATEGORY_NAME = "category"
const val LINK_TO_THE_STORE = "link"

sealed class Screens(val route: String) {

    object CategoriesScreen : Screens("categories_screen")

    object BooksScreen : Screens(route = "books_screen/{$CATEGORY_NAME}") {
        fun passCategoryName(categoryName: String): String {
            return this.route.replace(oldValue = "{$CATEGORY_NAME}", newValue = categoryName)
        }
    }

    object StoreScreen : Screens(route = "store_screen/{$LINK_TO_THE_STORE}") {
        fun passLinkToTheStore(linkToTheStore: String): String {
            return this.route.replace(oldValue = "{$LINK_TO_THE_STORE}", newValue = linkToTheStore)
        }
    }

}
