package com.sina.ecommerce.ui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.sina.domain.model.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val events by viewModel.events.collectAsState(null)

    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            events?.let { event ->
                when (event) {
                    is HomeScreenEvents.NavigateToProductDetails -> {}
                    is HomeScreenEvents.ShowError -> event.errorMessage?.let { Text(text = it) }
                    is HomeScreenEvents.ShowProducts -> {
                        HomeContent(
                            featured = event.featured,
                            popularProduct = event.popularProducts
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeContent(featured: List<Product>, popularProduct: List<Product>) {
    LazyColumn {
        item {
            if (featured.isNotEmpty()) {
                HomeProductRow(title = "Featured", products = featured)
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (popularProduct.isNotEmpty()) {
                HomeProductRow(title = "Popular Product", products = popularProduct)
            }
        }
    }
}


@Composable
fun HomeProductRow(title: String, products: List<Product>) {
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(
                    Alignment.CenterStart
                )
            )
            Text(
                modifier = Modifier.align(
                    Alignment.CenterEnd
                ), text = "view all", style = MaterialTheme.typography.titleMedium
            )
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
    LazyRow {
        items(products) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(126.dp, height = 144.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = product.priceString,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )


        }
    }
}
