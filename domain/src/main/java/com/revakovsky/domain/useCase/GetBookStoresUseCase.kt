package com.revakovsky.domain.useCase

import com.revakovsky.domain.models.Store
import com.revakovsky.domain.repository.BooksRepository
import com.revakovsky.domain.util.DataResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBookStoresUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {

    suspend operator fun invoke(bookTitle: String): DataResult<List<Store>> =
        booksRepository.getStoresForTheBook(bookTitle)

}