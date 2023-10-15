package com.revakovsky.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.revakovsky.data.local.entities.BookEntity
import com.revakovsky.data.local.entities.CategoryEntity
import com.revakovsky.data.local.entities.StoreEntity

@Database(
    entities = [CategoryEntity::class, BookEntity::class, StoreEntity::class],
    version = 1,
)
internal abstract class BooksDb : RoomDatabase() {

    abstract val booksDao: BooksDao

}