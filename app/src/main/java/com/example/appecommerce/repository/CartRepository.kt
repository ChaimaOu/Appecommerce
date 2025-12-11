package com.example.appecommerce.repository

import com.example.appecommerce.dao.CartDao
import com.example.appecommerce.database.CartItemEntity

class CartRepository(private val dao: CartDao) {

    val cartItems = dao.getCartItems()

    suspend fun addToCart(item: CartItemEntity) {
        dao.insertItem(item)
    }

    suspend fun updateQuantity(id: Int, qty: Int) =
        dao.updateQuantity(id, qty)

    suspend fun removeItem(item: CartItemEntity) =
        dao.deleteItem(item)

    suspend fun clearCart() = dao.clearCart()
}
