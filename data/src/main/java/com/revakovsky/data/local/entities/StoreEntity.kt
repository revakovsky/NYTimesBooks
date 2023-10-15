package com.revakovsky.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class StoreEntity(
    @PrimaryKey(autoGenerate = false)
    val url: String,
    val storeName: String,
    val id: Int = 0,
    val bookTitle: String,
)
