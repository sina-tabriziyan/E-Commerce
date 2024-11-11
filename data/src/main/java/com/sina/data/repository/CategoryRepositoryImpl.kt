package com.sina.data.repository

import com.sina.domain.network.NetworkService
import com.sina.domain.network.ResultWrapper
import com.sina.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val networkService: NetworkService) : CategoryRepository {
    override suspend fun getCategories(): ResultWrapper<List<String>> {
        return networkService.getCategories()
    }
}