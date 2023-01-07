package com.example.shopapp.data.source

import com.example.shopapp.data.network.StoreApi
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    val storeApi : StoreApi
) {


}