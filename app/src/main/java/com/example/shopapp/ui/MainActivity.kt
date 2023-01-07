package com.example.shopapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.shopapp.R
import com.example.shopapp.utils.NetworkResult
import com.example.shopapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                    Log.i(TAG,"response"+ it.data)

                }
                else -> {
                    Log.i(TAG,"something fishy")
                }
            }


        }
    }
}