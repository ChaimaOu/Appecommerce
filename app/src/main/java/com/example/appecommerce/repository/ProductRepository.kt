package com.example.appecommerce.repository

import com.example.appecommerce.data.remote.RetrofitInstance
import com.example.appecommerce.model.Product

class ProductRepository {

    private val api = RetrofitInstance.api

    suspend fun getProducts(): List<Product> {
        val response = api.getProducts()
        return response.products
    }

}
