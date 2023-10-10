package com.revakovsky.data

import android.util.Log
import com.revakovsky.data.local.BooksDb
import com.revakovsky.data.local.entities.BookEntity
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

            Log.d("TAG_Max", "BooksRepositoryImpl.kt: Exception e = ${e.message}")
            Log.d("TAG_Max", "")

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

            Log.d(
                "TAG_Max",
                "BooksRepositoryImpl.kt: getBooksFromCategory categoryWithBooks = $categoryWithBooks"
            )
            Log.d("TAG_Max", "")

            books = categoryWithBooks.books.map { it.mapToBook() }

            Log.d(
                "TAG_Max",
                "BooksRepositoryImpl.kt: getBooksFromCategory books = ${books.joinToString()}"
            )
            Log.d("TAG_Max", "")

        }
        return DataResult.Success(data = books)
    }

    override suspend fun getStoresForTheBook(bookTitle: String): DataResult<List<Store>> {
        var stores = emptyList<Store>()
        return try {
            dao.getBookWithStores(bookTitle).forEach { bookWithStores ->
                stores = bookWithStores.stores.map { it.mapToStore() }

                Log.d(
                    "TAG_Max",
                    "BooksRepositoryImpl.kt: getStoresForTheBook stores = ${stores.joinToString()}"
                )

            }
            DataResult.Success(data = stores)
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun processRemoteData(remoteData: ResultsDto) {
        val completableDeferred = CompletableDeferred<Unit>()
        val completableDeferred2 = CompletableDeferred<Unit>()
        val completableDeferred3 = CompletableDeferred<Unit>()

        clearLocalDb()
        insertCategoryEntitiesIntoDb(remoteData, completableDeferred)
        insertBookEntitiesIntoDb(remoteData, completableDeferred2)
        insertStoreEntitiesIntoDb(remoteData, completableDeferred3)

        completableDeferred.await()
        completableDeferred2.await()
        completableDeferred3.await()

        Log.d("TAG_Max", "BooksRepositoryImpl.kt: start getCategories()")
        Log.d("TAG_Max", "")

    }

    private suspend fun clearLocalDb() {
        booksDb.booksDao.apply {
            clearCategories()
            clearBooks()
            clearStores()

            Log.d("TAG_Max", "BooksRepositoryImpl.kt: finish clearLocalDb")
            Log.d("TAG_Max", "")

        }
    }

    private suspend fun insertCategoryEntitiesIntoDb(
        remoteData: ResultsDto,
        completableDeferred: CompletableDeferred<Unit>,
    ) {
        coroutineScope {

            Log.d("TAG_Max", "BooksRepositoryImpl.kt: start insertCategoryEntitiesIntoDb")
            Log.d("TAG_Max", "")

            val publishedDate = remoteData.publishedDate
            val categoryEntities =
                remoteData.categories.map { it.mapToCategoryEntity(publishedDate) }
            categoryEntities.forEach { categoryEntity ->
                dao.insertBooksCategory(categoryEntity)
                completableDeferred.complete(Unit)

                Log.d("TAG_Max", "BooksRepositoryImpl.kt: finish insertCategoryEntitiesIntoDb")
                Log.d("TAG_Max", "")

            }
        }
    }

    private suspend fun insertBookEntitiesIntoDb(
        remoteData: ResultsDto,
        completableDeferred2: CompletableDeferred<Unit>,
    ) {
        coroutineScope {

            Log.d("TAG_Max", "BooksRepositoryImpl.kt: start insertBookEntitiesIntoDb")
            Log.d("TAG_Max", "")

            val allBooksEntities = mutableListOf<BookEntity>()
            remoteData.categories.forEach { categoryDto ->
                val categoryName = categoryDto.categoryName
                categoryDto.books.map { it.mapToBookEntity(categoryName) }.forEach { bookEntities ->
                    allBooksEntities.add(bookEntities)
                }
            }
            allBooksEntities.forEach { bookEntity ->
                dao.insertBook(bookEntity)
            }
            completableDeferred2.complete(Unit)

            Log.d("TAG_Max", "BooksRepositoryImpl.kt: finish insertBookEntitiesIntoDb")
            Log.d("TAG_Max", "")
        }
    }

    private suspend fun insertStoreEntitiesIntoDb(
        remoteData: ResultsDto,
        completableDeferred3: CompletableDeferred<Unit>,
    ) {
        coroutineScope {

            Log.d("TAG_Max", "BooksRepositoryImpl.kt: start insertStoreEntitiesIntoDb")
            Log.d("TAG_Max", "")

            remoteData.categories.forEach { categoryDto ->
                categoryDto.books.forEach { bookDto ->
                    val bookTitle = bookDto.title
                    val storeEntities = bookDto.stores.map { it.mapToStoreEntity(bookTitle) }
                    storeEntities.forEach { storeEntity ->
                        dao.insertStore(storeEntity)
                        completableDeferred3.complete(Unit)

                        Log.d("TAG_Max", "BooksRepositoryImpl.kt: finish insertStoreEntitiesIntoDb")
                        Log.d("TAG_Max", "")

                    }
                }
            }
        }
    }

}