package com.example.shopapp.data.network

import com.example.shopapp.data.network.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreApi {


    @GET("/products")
    suspend fun getAllProducts() : Response<List<ProductsResponse>>

    @GET("/products/categories")
    suspend fun getAllCategories() : Response<List<String>>

    @GET("/products/category/{category}")
    suspend fun getSpecificCategory(@Path ("category") category: String) : Response<List<ProductsResponse>>

}