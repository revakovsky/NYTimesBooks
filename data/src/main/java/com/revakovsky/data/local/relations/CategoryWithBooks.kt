package com.revakovsky.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.revakovsky.data.local.entities.BookEntity
import com.revakovsky.data.local.entities.CategoryEntity

data class CategoryWithBooks(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "categoryName"
    )
    val books: List<BookEntity>
)
