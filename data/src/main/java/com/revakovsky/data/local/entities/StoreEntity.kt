package com.revakovsky.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class StoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val storeName: String,
    val bookTitle: String,
    val url: String,
)
