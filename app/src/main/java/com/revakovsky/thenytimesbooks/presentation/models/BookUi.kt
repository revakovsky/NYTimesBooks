package com.revakovsky.thenytimesbooks.presentation.models

data class BookUi(
    val id: Int = 0,
    val bookTitle: String = "",
    val author: String = "",
    val description: String = "",
    val publisher: String = "",
    val image: String = "",
    val rank: Int = 0,
)
