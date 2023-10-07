package com.revakovsky.data.remote.dto

import com.google.gson.annotations.SerializedName

internal data class BooksOverviewDto(
    @SerializedName("results") val results: Results,
)

data class Results(
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("lists") val categories: List<Lists>,
)

data class Lists(
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
    @SerializedName("buy_links") val buyLinks: List<BuyLink>,
)

data class BuyLink(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)