package com.revakovsky.data

import android.util.Log
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
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

internal class BooksRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val booksDb: BooksDb,
    private val exceptionHandler: ExceptionHandler,
) : BooksRepository {

    private val dao = booksDb.booksDao

    override suspend fun getBookCategories(shouldUpdateBooksInfo: Boolean): DataResult<List<Category>> {
        return try {
            if (shouldUpdateBooksInfo) updateBooksInfo()
            else provideBookCategories()
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun updateBooksInfo(): DataResult<List<Category>> {
        return try {
            val booksOverview = apiService.getBooksOverview()
            processRemoteData(booksOverview.results)
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun provideBookCategories(): DataResult<List<Category>> {
        val categoryEntities = dao.getCategories()

        return if (categoryEntities.isNotEmpty()) {
            val categories = categoryEntities.map { it.mapToCategory() }
            DataResult.Success(categories)
        } else updateBooksInfo()
    }

    override suspend fun getBooksFromCategory(categoryName: String): DataResult<List<Book>> {
        var books = emptyList<Book>()
        return try {
            dao.getCategoryWithBooks(categoryName).forEach { categoryWithBooks ->
                books = categoryWithBooks.books.map { it.mapToBook() }

                Log.d("TAG_Max", "BooksRepositoryImpl.kt: getBooksFromCategory books = ${books.joinToString()}")

            }
            DataResult.Success(data = books)
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    override suspend fun getStoresForTheBook(bookTitle: String): DataResult<List<Store>> {
        var stores = emptyList<Store>()
        return try {
            dao.getBookWithStores(bookTitle).forEach { bookWithStores ->
                stores = bookWithStores.stores.map { it.mapToStore() }

                Log.d("TAG_Max", "BooksRepositoryImpl.kt: getStoresForTheBook stores = ${stores.joinToString()}")

            }
            DataResult.Success(data = stores)
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun processRemoteData(remoteData: ResultsDto): DataResult<List<Category>> {
        val completableDeferred = CompletableDeferred<Unit>()

        clearLocalDb()
        insertCategoryEntitiesIntoDb(remoteData, completableDeferred)
        insertBookEntitiesIntoDb(remoteData)
        insertStoreEntitiesIntoDb(remoteData)

        completableDeferred.await()
        val categories = dao.getCategories().map { it.mapToCategory() }
        return DataResult.Success(categories)
    }

    private suspend fun clearLocalDb() {
        booksDb.booksDao.apply {
            clearCategories()
            clearBooks()
            clearStores()
        }
    }

    private suspend fun insertCategoryEntitiesIntoDb(
        remoteData: ResultsDto,
        completableDeferred: CompletableDeferred<Unit>,
    ) {
        coroutineScope {
            val publishedDate = remoteData.publishedDate
            val categoryEntities =
                remoteData.categories.map { it.mapToCategoryEntity(publishedDate) }
            categoryEntities.forEach { categoryEntity ->
                dao.insertBooksCategory(categoryEntity)
                completableDeferred.complete(Unit)
            }
        }
    }

    private suspend fun insertBookEntitiesIntoDb(remoteData: ResultsDto) {
        coroutineScope {
            remoteData.categories.forEach { categoryDto ->
                val categoryName = categoryDto.categoryName
                val bookEntities = categoryDto.books.map { it.mapToBookEntity(categoryName) }
                bookEntities.forEach { bookEntity ->
                    dao.insertBook(bookEntity)
                }
            }
        }
    }

    private suspend fun insertStoreEntitiesIntoDb(remoteData: ResultsDto) {
        coroutineScope {
            remoteData.categories.forEach { categoryDto ->
                categoryDto.books.forEach { bookDto ->
                    val bookTitle = bookDto.title
                    val storeEntities = bookDto.stores.map { it.mapToStoreEntity(bookTitle) }
                    storeEntities.forEach { storeEntity ->
                        dao.insertStore(storeEntity)
                    }
                }
            }
        }
    }

}