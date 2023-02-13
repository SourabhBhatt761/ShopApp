package com.example.shopapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopapp.data.Repository
import com.example.shopapp.data.network.model.ProductsResponse
import com.example.shopapp.utils.NetworkResult
import com.example.shopapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
    ) : AndroidViewModel(application) {

    private val _products = MutableLiveData<NetworkResult<List<ProductsResponse>>>()
    val apiProducts: LiveData<NetworkResult<List<ProductsResponse>>> = _products
    private val _categories = MutableLiveData<NetworkResult<List<String>>>()
    val apiCategories: LiveData<NetworkResult<List<String>>> = _categories
    private val _specificCategory = MutableLiveData<NetworkResult<List<ProductsResponse>>>()
    val specificCategory: LiveData<NetworkResult<List<ProductsResponse>>> = _specificCategory


    fun getAllProducts() {

        viewModelScope.launch(Dispatchers.IO) {

            //set the loading state
            _products.postValue(NetworkResult.Loading())

            //do network call
            val response = repository.remote.getAllProducts()

            //set the value
            _products.postValue(handleProductsResponse(response))
        }
    }

    fun getAllCategories() {

        if (Utils.hasInternetConnection(getApplication())) {

            fetchCategoriesOnline()

        }else{

            fetchCategoriesFromDB()
        }
    }

    private fun fetchCategoriesFromDB() {


    }

    private fun fetchCategoriesOnline() {

        viewModelScope.launch(Dispatchers.IO) {

            //set the loading state
            _categories.postValue(NetworkResult.Loading())

            //do network call
            val response = repository.remote.getAllCategories()

            //set the value
            _categories.postValue(handleCategoriesResponse(response))
        }

    }

    fun getSpecificCategory(category: String){

        viewModelScope.launch(Dispatchers.IO) {

            //set the loading state
            _specificCategory.postValue(NetworkResult.Loading())

            //do network call
            val response = repository.remote.getSpecificCategory(category)

            //set the value
            _specificCategory.postValue(handleProductsResponse(response))

        }

    }


    private fun handleProductsResponse(response: Response<List<ProductsResponse>>): NetworkResult<List<ProductsResponse>> {
        return if (response.isSuccessful) {
            NetworkResult.Success(response.body()!!)
        } else if (response.message().toString().contains("timeout")) {
            NetworkResult.Error("Timeout")
        } else {
            NetworkResult.Error(response.message())
        }
    }

    private fun handleCategoriesResponse(response: Response<List<String>>): NetworkResult<List<String>> {
        return if (response.isSuccessful) {
            NetworkResult.Success(response.body()!!)
        } else if (response.message().toString().contains("timeout")) {
            NetworkResult.Error("Timeout")
        } else {
            NetworkResult.Error(response.message())
        }
    }
}