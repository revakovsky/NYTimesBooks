package com.revakovsky.domain.useCase

import com.revakovsky.domain.models.Category
import com.revakovsky.domain.repository.BooksRepository
import com.revakovsky.domain.util.DataResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBookCategoriesUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {

    suspend operator fun invoke(): DataResult<List<Category>> = booksRepository.getBookCategories()

}