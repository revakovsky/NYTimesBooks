package com.revakovsky.thenytimesbooks.utils

import com.revakovsky.domain.models.Book
import com.revakovsky.domain.models.Category
import com.revakovsky.domain.models.Store
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.presentation.models.CategoryUi
import com.revakovsky.thenytimesbooks.presentation.models.StoreUi

internal fun Category.mapToCategoryUi(): CategoryUi {
    return CategoryUi(
        categoryName = this.categoryName,
        howOftenIsItUpdated = this.howOftenIsItUpdated,
        publishedDate = this.publishedDate
    )
}

internal fun Book.mapToBookUi(): BookUi {
    return BookUi(
        id = this.id,
        bookTitle = this.bookTitle,
        author = this.author,
        description = this.description,
        publisher = this.publisher,
        image = this.image,
        rank = this.rank
    )
}

internal fun Store.mapToStoreUi(): StoreUi {
    return StoreUi(
        storeName = this.storeName,
        url = this.url
    )
}
