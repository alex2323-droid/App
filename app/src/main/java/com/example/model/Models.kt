package com.example.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val isPromotion: Boolean = false
)

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)
