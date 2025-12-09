package com.example.appecommerce.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Double,
    val description: String,
    val category: String,
    val color: String,
    @SerializedName("imageUrl")
    val image: String,
    val isNew: Boolean
)
