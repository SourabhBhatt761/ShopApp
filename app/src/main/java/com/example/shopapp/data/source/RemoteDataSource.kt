package com.example.shopapp.data.source

import com.example.shopapp.data.network.StoreApi
import com.example.shopapp.data.network.model.ProductsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val storeApi : StoreApi
){


    suspend fun getAllProducts() : Response<List<ProductsResponse>> {
        return storeApi.getAllProducts();
    }

    suspend fun getAllCategories() : Response<List<String>> {
        return storeApi.getAllCategories();
    }

    suspend fun getSpecificCategory(category : String) : Response<List<ProductsResponse>> {
        return storeApi.getSpecificCategory(category)
    }
}