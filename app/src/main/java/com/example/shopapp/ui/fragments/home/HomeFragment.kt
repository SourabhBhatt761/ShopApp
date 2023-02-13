package com.example.shopapp.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shopapp.data.network.model.ProductsResponse
import com.example.shopapp.databinding.ChoiceChipBinding
import com.example.shopapp.databinding.FragmentHomeBinding
import com.example.shopapp.ui.adapters.ProductsAdapter
import com.example.shopapp.utils.NetworkResult
import com.example.shopapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()
    private val TAG = "HomeFragment"
    private val productsAdapter by lazy { ProductsAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)


        fetchAllCategories()
        chipSelectionListener()
        fetchAllProductList()

        return binding.root
    }


    private fun fetchAllCategories() {

        mainViewModel.getAllCategories()

        mainViewModel.apiCategories.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let { createChipsForCategories(it) }

                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
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

        mainViewModel.specificCategory.observe(viewLifecycleOwner) { response ->

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
        mainViewModel.apiProducts.observe(viewLifecycleOwner) { response ->

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
            requireContext(),
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