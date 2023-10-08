package com.revakovsky.domain.repository

import com.revakovsky.domain.models.Book
import com.revakovsky.domain.models.Category
import com.revakovsky.domain.models.Store
import com.revakovsky.domain.util.DataResult

interface BooksRepository {

    suspend fun getBookCategories(): DataResult<List<Category>>
    suspend fun getBooksFromCategory(categoryName: String): DataResult<List<Book>>
    suspend fun getStoresForTheBook(bookTitle: String): DataResult<List<Store>>

}