package com.example.appecommerce.dao

import androidx.room.*
import com.example.appecommerce.database.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartItemEntity)

    @Query("UPDATE cart_items SET quantity = quantity + 1 WHERE id = :id")
    suspend fun increaseQuantity(id: Int)

    @Query("UPDATE cart_items SET quantity = quantity - 1 WHERE id = :id AND quantity > 1")
    suspend fun decreaseQuantity(id: Int)

    @Query("UPDATE cart_items SET quantity = :qty WHERE id = :id")
    suspend fun updateQuantity(id: Int, qty: Int)

    @Delete
    suspend fun deleteItem(item: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()



}
