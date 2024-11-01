package com.sina.domain.usecase

import com.sina.domain.repository.ProductRepository

class GetProductUseCase(private val repository: ProductRepository) {
    suspend fun execute(category: String?) = repository.getProducts(category)
}