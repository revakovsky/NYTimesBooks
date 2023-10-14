package com.revakovsky.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookTitle: String,
    val author: String,
    val description: String,
    val publisher: String,
    val image: String? = null,
    val rank: Int,
    val categoryName: String,
)
