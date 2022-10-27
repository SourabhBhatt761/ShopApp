package com.example.shopapp.data.source

import com.example.shopapp.data.network.ProductsApi
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    val productsApi : ProductsApi
) {


}