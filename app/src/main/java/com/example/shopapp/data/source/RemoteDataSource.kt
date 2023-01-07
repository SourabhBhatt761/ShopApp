package com.example.shopapp.data.source

import com.example.shopapp.data.network.StoreApi
import com.example.shopapp.data.network.model.ProductsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val storeApi : StoreApi
){


    suspend fun getAllProducts() : Response<ProductsResponse> {
        return storeApi.getAllProducts();
    }
}