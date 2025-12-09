package com.example.appecommerce.data.remote

import com.example.appecommerce.model.ApiResponse
import com.example.appecommerce.model.Product
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): ApiResponse

}
