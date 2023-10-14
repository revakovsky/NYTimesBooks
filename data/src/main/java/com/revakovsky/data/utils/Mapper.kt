package com.revakovsky.data.utils

import com.revakovsky.data.local.entities.BookEntity
import com.revakovsky.data.local.entities.CategoryEntity
import com.revakovsky.data.local.entities.StoreEntity
import com.revakovsky.data.remote.dto.BookDto
import com.revakovsky.data.remote.dto.CategoriesDto
import com.revakovsky.data.remote.dto.StoreDto
import com.revakovsky.domain.models.Book
import com.revakovsky.domain.models.Category
import com.revakovsky.domain.models.Store

internal fun CategoriesDto.mapToCategoryEntity(publishedDate: String): CategoryEntity {
    return CategoryEntity(
        categoryName = this.categoryName,
        howOftenIsItUpdated = this.howOftenIsItUpdated,
        publishedDate = publishedDate
    )
}

internal fun BookDto.mapToBookEntity(categoryName: String): BookEntity {
    return BookEntity(
        bookTitle = this.title,
        author = this.author,
        description = this.description,
        publisher = this.publisher,
        image = this.image,
        rank = this.rank,
        categoryName = categoryName
    )
}

internal fun StoreDto.mapToStoreEntity(bookTitle: String): StoreEntity {
    return StoreEntity(
        storeName = this.storeName,
        bookTitle = bookTitle,
        url = this.url
    )
}

internal fun CategoryEntity.mapToCategory(): Category {
    return Category(
        categoryName = this.categoryName,
        howOftenIsItUpdated = this.howOftenIsItUpdated,
        publishedDate = this.publishedDate
    )
}

internal fun BookEntity.mapToBook(): Book {
    return Book(
        id = this.id,
        bookTitle = this.bookTitle,
        author = this.author,
        description = this.description,
        publisher = this.publisher,
        image = this.image.orEmpty(),
        rank = this.rank
    )
}

internal fun StoreEntity.mapToStore(): Store {
    return Store(
        storeName = this.storeName,
        url = this.url
    )
}
