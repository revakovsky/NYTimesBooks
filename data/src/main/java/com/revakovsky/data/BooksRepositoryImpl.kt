package com.revakovsky.data

import com.revakovsky.data.local.BooksDb
import com.revakovsky.data.remote.ApiService
import com.revakovsky.data.remote.dto.ResultsDto
import com.revakovsky.data.utils.ExceptionHandler
import com.revakovsky.data.utils.mapToBook
import com.revakovsky.data.utils.mapToBookEntity
import com.revakovsky.data.utils.mapToCategory
import com.revakovsky.data.utils.mapToCategoryEntity
import com.revakovsky.data.utils.mapToStore
import com.revakovsky.data.utils.mapToStoreEntity
import com.revakovsky.domain.models.Book
import com.revakovsky.domain.models.Category
import com.revakovsky.domain.models.Store
import com.revakovsky.domain.repository.BooksRepository
import com.revakovsky.domain.util.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class BooksRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val booksDb: BooksDb,
    private val exceptionHandler: ExceptionHandler,
) : BooksRepository {

    private val dao = booksDb.booksDao

    override suspend fun getBookCategories(shouldUpdateCategories: Boolean): DataResult<List<Category>> {
        return try {
            if (shouldUpdateCategories) updateCategoriesInfo()
            else provideBookCategories()
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun updateCategoriesInfo(): DataResult<List<Category>> {
        downLoadNewBooksOverview()
        val categories = dao.getCategories().map { it.mapToCategory() }
        return DataResult.Success(categories)
    }

    private suspend fun provideBookCategories(): DataResult<List<Category>> {
        val categoryEntities = dao.getCategories()

        return if (categoryEntities.isNotEmpty()) {
            val categories = categoryEntities.map { it.mapToCategory() }
            DataResult.Success(categories)
        } else updateCategoriesInfo()
    }

    private suspend fun downLoadNewBooksOverview() {
        try {
            val booksOverview = apiService.getBooksOverview()
            processRemoteData(booksOverview.results)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBooksFromCategory(
        categoryName: String,
        shouldUpdateBooks: Boolean,
    ): DataResult<List<Book>> {
        return try {
            if (shouldUpdateBooks) updateBooksInfo(categoryName)
            else provideBooks(categoryName)
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun updateBooksInfo(categoryName: String): DataResult<List<Book>> {
        downLoadNewBooksOverview()
        return provideBooks(categoryName)
    }

    private suspend fun provideBooks(categoryName: String): DataResult<List<Book>> {
        var books = emptyList<Book>()
        dao.getCategoryWithBooks(categoryName).forEach { categoryWithBooks ->
            books = categoryWithBooks.books.map { it.mapToBook() }
        }
        return DataResult.Success(data = books)
    }

    override suspend fun getStoresForTheBook(bookTitle: String): DataResult<List<Store>> {
        var stores = emptyList<Store>()
        return try {
            dao.getBookWithStores(bookTitle).forEach { bookWithStores ->
                stores = bookWithStores.stores.map { it.mapToStore() }
            }
            DataResult.Success(data = stores)
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun processRemoteData(remoteData: ResultsDto) {
        clearLocalDb()

        withContext(Dispatchers.IO) {
            val deferred1 = async { insertCategoryEntitiesIntoDb(remoteData) }
            val deferred2 = async { insertBookEntitiesIntoDb(remoteData) }
            val deferred3 = async { insertStoreEntitiesIntoDb(remoteData) }
            awaitAll(deferred1, deferred2, deferred3)
        }
    }

    private suspend fun clearLocalDb() {
        booksDb.booksDao.apply {
            clearCategories()
            clearBooks()
            clearStores()
        }
    }

    private suspend fun insertCategoryEntitiesIntoDb(remoteData: ResultsDto) {
        val publishedDate = remoteData.publishedDate
        remoteData.categories.forEach { categoryDto ->
            dao.insertBooksCategory(categoryDto.mapToCategoryEntity(publishedDate))
        }
    }

    private suspend fun insertBookEntitiesIntoDb(remoteData: ResultsDto) {
        remoteData.categories.forEach { categoryDto ->
            val categoryName = categoryDto.categoryName
            categoryDto.books.forEach { bookDto ->
                dao.insertBook(bookDto.mapToBookEntity(categoryName))
            }
        }
    }

    private suspend fun insertStoreEntitiesIntoDb(remoteData: ResultsDto) {
        remoteData.categories.forEach { categoryDto ->
            categoryDto.books.forEach { bookDto ->
                val bookTitle = bookDto.title
                bookDto.stores.forEach { storeDto ->
                    dao.insertStore(storeDto.mapToStoreEntity(bookTitle))
                }
            }
        }
    }

}