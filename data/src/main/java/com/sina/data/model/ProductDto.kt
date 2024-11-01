package com.sina.data.model

import com.sina.domain.model.Product
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class ProductDto(
    val id: Long,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
) {
    fun toProduct() = Product(
        id = id,
        title = title,
        price = price,
        category = category,
        description = description,
        image = image
    )
}
