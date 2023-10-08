package com.revakovsky.data.remote.dto

import com.google.gson.annotations.SerializedName

internal data class BooksOverviewDto(
    @SerializedName("results") val results: Results,
)

data class Results(
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("lists") val categories: List<Categories>,
)

data class Categories(
    @SerializedName("display_name") val categoryName: String,
    @SerializedName("updated") val howOftenIsItUpdated: String,
    @SerializedName("books") val books: List<Book>,
)

data class Book(
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("description") val description: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("book_image") val image: String,
    @SerializedName("rank") val rank: Int,
    @SerializedName("buy_links") val stores: List<Store>,
)

data class Store(
    @SerializedName("name") val storeName: String,
    @SerializedName("url") val url: String,
)
