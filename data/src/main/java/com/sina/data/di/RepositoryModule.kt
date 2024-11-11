package com.sina.data.di

import com.sina.data.repository.CategoryRepositoryImpl
import com.sina.data.repository.ProductRepositoryImpl
import com.sina.domain.repository.CategoryRepository
import com.sina.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
}