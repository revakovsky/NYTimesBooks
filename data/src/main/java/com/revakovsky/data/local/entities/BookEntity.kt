package com.revakovsky.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey
    val id: Int,
    val bookTitle: String,
    val author: String,
    val description: String,
    val publisher: String,
    val image: String,
    val rank: Int,
    val categoryName: String,
)
