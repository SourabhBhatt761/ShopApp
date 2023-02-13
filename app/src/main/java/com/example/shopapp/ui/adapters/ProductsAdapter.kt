package com.example.shopapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shopapp.R
import com.example.shopapp.data.network.model.ProductsResponse
import com.example.shopapp.databinding.ProductsItemLayoutBinding
import com.example.shopapp.utils.MyDiffUtil

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var products = listOf<ProductsResponse>()

    fun setData(newData: List<ProductsResponse>) {
        val recipeDiffUtil = MyDiffUtil(products, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipeDiffUtil)
        products = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ProductsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = products[position]
        holder.binding.apply {

            //load image using coil
            productIv.load(item.image) {
                placeholder(R.drawable.place_holder_img)
                crossfade(true)
                error(R.drawable.place_holder_img)
            }

            titleTv.text = item.title

            categoryTv.text = item.category
        }


    }

    override fun getItemCount(): Int {
        return products.size
    }
}