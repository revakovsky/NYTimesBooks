package com.revakovsky.domain.models

data class Book(
    val id: Int,
    val bookTitle: String,
    val author: String,
    val description: String,
    val publisher: String,
    val image: String,
    val rank: Int,
)
