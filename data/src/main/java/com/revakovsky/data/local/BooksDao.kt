package com.revakovsky.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.revakovsky.data.local.entities.BookEntity
import com.revakovsky.data.local.entities.CategoryEntity
import com.revakovsky.data.local.entities.StoreEntity
import com.revakovsky.data.local.relations.BookWithStores
import com.revakovsky.data.local.relations.CategoryWithBooks

@Dao
internal interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooksCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(store: StoreEntity)

    @Transaction
    @Query("SELECT * FROM CategoryEntity")
    suspend fun getCategories(): List<CategoryEntity>

    @Transaction
    @Query("SELECT * FROM CategoryEntity WHERE categoryName = :categoryName")
    suspend fun getCategoryWithBooks(categoryName: String): List<CategoryWithBooks>

    @Transaction
    @Query("SELECT * FROM BookEntity WHERE bookTitle = :bookTitle")
    suspend fun getBookWithStores(bookTitle: String): List<BookWithStores>

    @Query("DELETE FROM CategoryEntity")
    suspend fun clearCategories()

    @Query("DELETE FROM BookEntity")
    suspend fun clearBooks()

    @Query("DELETE FROM StoreEntity")
    suspend fun clearStores()

}