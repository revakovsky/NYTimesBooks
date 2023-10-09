package com.revakovsky.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val categoryName: String,
    val howOftenIsItUpdated: String,
    val publishedDate: String,
)
