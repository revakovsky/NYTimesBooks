package com.revakovsky.data

import com.revakovsky.data.local.BooksDb
import com.revakovsky.data.remote.ApiService
import com.revakovsky.data.utils.ExceptionHandler
import com.revakovsky.domain.models.Book
import com.revakovsky.domain.models.Category
import com.revakovsky.domain.models.Store
import com.revakovsky.domain.repository.BooksRepository
import com.revakovsky.domain.util.DataResult
import javax.inject.Inject

internal class BooksRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val booksDb: BooksDb,
    private val exceptionHandler: ExceptionHandler,
) : BooksRepository {

    override suspend fun getBookCategories(shouldUpdateBooksInfo: Boolean): DataResult<List<Category>> {
        return try {
            if (shouldUpdateBooksInfo) updateBooksInfo()
            else provideBookCategories()
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private fun updateBooksInfo(): DataResult<List<Category>> {

        return DataResult.Success(
            data = listOf(
                Category(
                    categoryName = "TEST CATEGORY",
                    howOftenIsItUpdated = "twice"
                )
            )
        )
    }

    private fun provideBookCategories(): DataResult<List<Category>> {
        return DataResult.Success(
            data = listOf(
                Category(
                    categoryName = "TEST CATEGORY",
                    howOftenIsItUpdated = "twice"
                )
            )
        )
    }

    override suspend fun getBooksFromCategory(categoryName: String): DataResult<List<Book>> {

        return DataResult.Success(
            data = listOf(
                Book(
                    id = 1,
                    bookTitle = "TEST TITLE",
                    author = "Test Author",
                    description = "Test description",
                    publisher = "PUBLISHER TEST",
                    image = "test",
                    rank = 55
                )
            )
        )
    }

    override suspend fun getStoresForTheBook(bookTitle: String): DataResult<List<Store>> {

        return DataResult.Success(
            data = listOf(
                Store(
                    storeName = "TEST STORE NAME",
                    url = "test URL for the book"
                )
            )
        )
    }

}