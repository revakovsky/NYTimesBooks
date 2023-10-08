package com.revakovsky.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val categoryName: String,
    val howOftenIsItUpdated: String,
)
