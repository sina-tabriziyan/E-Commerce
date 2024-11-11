package com.sina.domain.usecase

import com.sina.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val repository: CategoryRepository) {
    suspend fun execute() = repository.getCategories()
}