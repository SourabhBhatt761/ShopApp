package com.example.shopapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapp.databinding.ActivityMainBinding
import com.example.shopapp.utils.NetworkResult
import com.example.shopapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val productsAdapter by lazy { ProductsAdapter() }
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchAllProductList()

    }

    private fun fetchAllProductList() {

        //api call
        mainViewModel.getAllProducts()

        showShimmerEffect()

        //fetch the data
        mainViewModel.apiProducts.observe(this) { response ->

            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let { productsAdapter.setData(it) }
                    binding.recyclerView.adapter = productsAdapter

                    Log.i(TAG, "response" + response.data)
                    hideShimmerEffect()
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        this,
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun hideShimmerEffect() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
    }

    private fun showShimmerEffect() {
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE

    }
}