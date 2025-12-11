package com.example.appecommerce.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appecommerce.dao.CartDao
import com.example.appecommerce.database.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
