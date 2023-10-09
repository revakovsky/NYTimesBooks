package com.revakovsky.data.remote.dto

import com.google.gson.annotations.SerializedName

internal data class BooksOverviewDto(
    @SerializedName("results") val results: ResultsDto,
)

internal data class ResultsDto(
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("lists") val categories: List<CategoriesDto>,
)

internal data class CategoriesDto(
    @SerializedName("display_name") val categoryName: String,
    @SerializedName("updated") val howOftenIsItUpdated: String,
    @SerializedName("books") val books: List<BookDto>,
)

internal data class BookDto(
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("description") val description: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("book_image") val image: String,
    @SerializedName("rank") val rank: Int,
    @SerializedName("buy_links") val stores: List<StoreDto>,
)

internal data class StoreDto(
    @SerializedName("name") val storeName: String,
    @SerializedName("url") val url: String,
)
