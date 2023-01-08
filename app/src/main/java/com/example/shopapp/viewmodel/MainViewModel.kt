package com.example.shopapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.data.Repository
import com.example.shopapp.data.network.model.ProductsResponse
import com.example.shopapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _products = MutableLiveData<NetworkResult<List<ProductsResponse>>>()
    val apiProducts : LiveData<NetworkResult<List<ProductsResponse>>> = _products


    fun getAllProducts(){

        viewModelScope.launch(Dispatchers.IO) {

            //set the loading state
            _products.postValue(NetworkResult.Loading())

            //do network call
            val response = repository.remote.getAllProducts()

            //set the value
            _products.postValue(handleNetworkResponse(response))
        }
    }


    private fun handleNetworkResponse(response: Response<List<ProductsResponse>>): NetworkResult<List<ProductsResponse>> {
        return if (response.isSuccessful) {
            NetworkResult.Success(response.body()!!)
        } else if (response.message().toString().contains("timeout")) {
            NetworkResult.Error("Timeout")
        } else {
            NetworkResult.Error(response.message())
        }
    }
}