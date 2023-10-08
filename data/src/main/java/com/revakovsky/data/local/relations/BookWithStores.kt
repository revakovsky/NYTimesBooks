package com.revakovsky.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.revakovsky.data.local.entities.BookEntity
import com.revakovsky.data.local.entities.StoreEntity

data class BookWithStores(
    @Embedded val book: BookEntity,
    @Relation(
        parentColumn = "bookTitle",
        entityColumn = "bookTitle"
    )
    val stores: List<StoreEntity>,
)
