package com.sina.ecommerce.di

import com.sina.ecommerce.ui.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModel = module {
    viewModel {
        HomeViewModel(get())
    }
}