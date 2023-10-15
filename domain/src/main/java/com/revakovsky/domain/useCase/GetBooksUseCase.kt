package com.revakovsky.domain.useCase

import com.revakovsky.domain.models.Book
import com.revakovsky.domain.repository.BooksRepository
import com.revakovsky.domain.util.DataResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {

    suspend operator fun invoke(categoryName: String, shouldUpdateBooks: Boolean): DataResult<List<Book>> =
        booksRepository.getBooksFromCategory(categoryName, shouldUpdateBooks)

}