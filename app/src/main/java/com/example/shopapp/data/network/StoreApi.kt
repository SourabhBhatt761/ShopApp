package com.example.shopapp.data.network

import com.example.shopapp.data.network.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface StoreApi {


    @GET("products")
    suspend fun getAllProducts() : Response<List<ProductsResponse>>

}