package com.sina.ecommerce.ui.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.domain.model.Product
import com.sina.domain.network.ResultWrapper
import com.sina.domain.usecase.GetCategoriesUseCase
import com.sina.domain.usecase.GetProductUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val categoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val eventChannel: Channel<HomeScreenEvents> = Channel()
    val events = eventChannel.receiveAsFlow()


    init {
        allProducts()
    }

    private fun allProducts() {
        viewModelScope.launch {
            val featured = products("electronics")
            val popular = products("jewelery")
            val categories = categories()
            if (featured.isEmpty() || popular.isEmpty()) {
                eventChannel.send(HomeScreenEvents.ShowError("Failed to load products"))
                return@launch
            }
            eventChannel.send(HomeScreenEvents.ShowProducts(featured, popular, categories))
        }
    }


    private suspend fun categories(): List<String> {
        return when (val result = categoriesUseCase.execute()) {
            is ResultWrapper.Failure -> emptyList()
            is ResultWrapper.Success -> result.value
        }

    }

    private suspend fun products(category: String): List<Product> {
        return when (val result = getProductUseCase.execute(category)) {
            is ResultWrapper.Failure -> emptyList()
            is ResultWrapper.Success -> result.value
        }
    }


}

sealed class HomeScreenEvents {
    data object NavigateToProductDetails : HomeScreenEvents()
    data class ShowProducts(
        val featured: List<Product>,
        val popularProducts: List<Product>,
        val categories: List<String>
    ) :
        HomeScreenEvents()

    data class ShowError(val errorMessage: String?) : HomeScreenEvents()
}