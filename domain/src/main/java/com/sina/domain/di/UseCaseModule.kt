package com.sina.domain.di

import com.sina.domain.usecase.GetProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductUseCase(get()) }
}