package com.sina.data.repository

import com.sina.domain.model.Product
import com.sina.domain.network.NetworkService
import com.sina.domain.network.ResultWrapper
import com.sina.domain.repository.ProductRepository

class ProductRepositoryImpl(private val networkService: NetworkService) : ProductRepository {
    override suspend fun getProducts(category: String?): ResultWrapper<List<Product>> =
        networkService.getProducts(category)
}