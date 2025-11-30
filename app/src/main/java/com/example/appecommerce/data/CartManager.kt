package com.example.appecommerce.data

import com.example.appecommerce.model.Product

object CartManager {

    val cartItems = mutableListOf<Product>()

    fun addToCart(product: Product) {
        cartItems.add(product)
    }
}
