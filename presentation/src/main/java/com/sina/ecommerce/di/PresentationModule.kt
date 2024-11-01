package com.sina.ecommerce.di

import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModel)
}