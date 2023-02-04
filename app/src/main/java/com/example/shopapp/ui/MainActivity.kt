package com.example.shopapp.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapp.data.network.model.ProductsResponse
import com.example.shopapp.databinding.ActivityMainBinding
import com.example.shopapp.databinding.ChoiceChipBinding
import com.example.shopapp.utils.NetworkResult
import com.example.shopapp.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
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

//        fetchAllProductList()

        fetchAllCategories()

        chipSelectionListener()
    }

    private fun fetchAllCategories() {

        mainViewModel.getAllCategories()

        mainViewModel.apiCategories.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let { createChipsForCategories(it) }

                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        this,
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }

    }

    private fun createChipsForCategories(data: List<String>) {

        for (category in data) {

            val chip = ChoiceChipBinding.inflate(layoutInflater).root
            chip.text = category
            binding.categoriesChipGroup.addView(chip)

            chip.setOnClickListener {

                showShimmerEffect()

                //input the selection
                mainViewModel.getSpecificCategory(chip.text.toString())

                chip.isChecked = true
            }

        }
    }

    private fun chipSelectionListener() {

        mainViewModel.specificCategory.observe(this) { response ->

            when (response) {
                is NetworkResult.Success -> {

                    Log.i(TAG, "response ${response.data}")

                    response.data?.let { showProductsList(it) }
                }
                is NetworkResult.Error -> {

                    showErrorInRecyclerView(response.message.toString())
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun fetchAllProductList() {

        //api call
        mainViewModel.getAllProducts()

        showShimmerEffect()

        //fetch the data
        mainViewModel.apiProducts.observe(this) { response ->

            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let { showProductsList(it) }
                }
                is NetworkResult.Error -> {

                    showErrorInRecyclerView(response.message.toString())
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun showErrorInRecyclerView(error : String) {

        hideShimmerEffect()
        Toast.makeText(
            this,
            error,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showProductsList(data : List<ProductsResponse>){

        Log.i(TAG, "response $data")
        productsAdapter.setData(data)

        binding.recyclerView.adapter = productsAdapter


        hideShimmerEffect()
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun hideShimmerEffect() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE

        binding.recyclerView.visibility = View.VISIBLE

    }

    private fun showShimmerEffect() {
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE

        binding.recyclerView.visibility = View.GONE
    }
}