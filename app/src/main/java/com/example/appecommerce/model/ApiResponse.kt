package com.example.appecommerce.model

data class ApiResponse(
    val success: Boolean,
    val category: String,
    val products: List<Product>,
    val count: Int
)
