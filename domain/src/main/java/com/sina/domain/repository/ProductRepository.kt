package com.sina.domain.repository

import com.sina.domain.model.Product
import com.sina.domain.network.ResultWrapper

interface ProductRepository {
    suspend fun getProducts(category: String?): ResultWrapper<List<Product>>
}