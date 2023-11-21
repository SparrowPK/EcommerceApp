package com.example.ecommerceapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemCardBinding
import com.example.ecommerceapp.model.ProductListItem
import android.graphics.Paint
import android.util.Log
import android.widget.ImageView
import com.example.ecommerceapp.R
import android.widget.RatingBar
import com.example.ecommerceapp.ui.ProductDetailActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class ProductAdapter(private val productList: List<ProductListItem>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private var itemCardBinding: ItemCardBinding) : RecyclerView.ViewHolder(itemCardBinding.root){
        val title_text: TextView = itemCardBinding.productTitle
        val price_text: TextView = itemCardBinding.productPrice
        val originalPrice_text: TextView = itemCardBinding.originalPrice
        val discountPercentage_text: TextView = itemCardBinding.discountPercentage
        val description_text: TextView = itemCardBinding.description
        val category_text: TextView = itemCardBinding.category
        val ratingBar: RatingBar = itemCardBinding.ratingBar
        val ratingCountText: TextView = itemCardBinding.ratingCount

        val productImageview: ImageView = itemCardBinding.productImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        //calculate percentage value
        //(current_price / original_price) × 100 = percentage

        val currentPrice = product.price
        val discountPercentage = (10..50).random()
        val originalPrice = (currentPrice/(100 - discountPercentage)) * 100

        holder.title_text.text = product.title
        holder.discountPercentage_text.text = "$discountPercentage% off"
        holder.originalPrice_text.text = originalPrice.toInt().toString()
        holder.originalPrice_text.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        holder.price_text.text = "$${product.price}"
        holder.description_text.text = product.description
        holder.category_text.text = product.category

//        holder.ratingBar.numStars = 5
        holder.ratingBar.rating = product.rating.rate
//        holder.ratingBar.stepSize = 0.1f

        holder.ratingCountText.text = "${product.rating.count} ratings"

        Log.d("Image_url", product.image)
        Log.d("product", product.toString())

        Picasso.get()
            .load(product.image)
            .placeholder(R.drawable.shoeimg)
            .into(holder.productImageview)

//        holder.itemView.setOnClickListener {
//            val intent = Intent(it.context, ProductDetailActivity::class.java)
//            intent.putExtra("product", product)
//            it.context.startActivity(intent)
//        }

        val gson = Gson()
        val productJson = gson.toJson(product)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra("product", productJson)
            intent.putExtra("discountPercentage", discountPercentage.toString())
            intent.putExtra("originalPrice", originalPrice.toInt().toString())
            it.context.startActivity(intent)
        }
    }
}

