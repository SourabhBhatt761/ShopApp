package com.example.shopapp.data.source

import com.example.shopapp.data.network.ProductsApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val productsApi : ProductsApi
){


}