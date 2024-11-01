package com.sina.domain.model

data class Product(
    val id: Long,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
) {
    val priceString: String
        get() = price.toString()
}