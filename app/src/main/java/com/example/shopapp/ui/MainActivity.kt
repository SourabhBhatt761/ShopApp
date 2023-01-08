package com.example.shopapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapp.databinding.ActivityMainBinding
import com.example.shopapp.utils.NetworkResult
import com.example.shopapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding : ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //old method
//        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

//was learning about threads and coroutines
//        thread {
//
//        }
//
//        CoroutineScope(Dispatchers.IO).launch {
//
//        }
//
//        GlobalScope.launch(Dispatchers.IO){
//
//        }

        Log.i(TAG,"products "+ mainViewModel.getAllProducts())
        mainViewModel.apiProducts.observe(this) {

            when(it){
                is NetworkResult.Success ->{

                    val productsAdapter = ProductsAdapter()
                    productsAdapter.setData(it.data!!)
                    binding.recyclerView.adapter = productsAdapter

                    Log.i(TAG,"response"+ it.data)

                }
                else -> {
                    Log.i(TAG,"something fishy")
                }
            }


        }
    }
}