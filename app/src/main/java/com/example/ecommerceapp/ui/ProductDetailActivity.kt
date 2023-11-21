package com.example.ecommerceapp.ui

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ProductDetailBinding
import com.example.ecommerceapp.model.ProductListItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import androidx.appcompat.widget.Toolbar

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var productDetailBinding: ProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDetailBinding = ProductDetailBinding.inflate(layoutInflater)
        setContentView(productDetailBinding.root)

        val productJson = intent.getStringExtra("product")
        val originalPrice = intent.getStringExtra("originalPrice")
        val discountPercentage = intent.getStringExtra("discountPercentage")

        val gson = Gson()
        val product = gson.fromJson(productJson, ProductListItem::class.java)

        Log.d("Product", product.toString())
        if (productJson != null) {
            Log.d("ProductJson", productJson)
        }

        val rating = product.rating.rate

        val ratingRank = when(rating){
            in 2.0..3.4 -> "Average  ·"
            in 3.5..4.4 -> "Good  ·"
            in 4.5..5.0 -> "Very Good  ·"
            else -> ""
        }

        productDetailBinding.productTitle.text = product.title
        productDetailBinding.productPrice.text = "$${product.price}"
        productDetailBinding.discountPercentage.text = "${discountPercentage}% off"
        productDetailBinding.originalPrice.text = originalPrice
        productDetailBinding.originalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        productDetailBinding.productDescription.text = product.description
        productDetailBinding.ratingBar.rating = rating
        productDetailBinding.ratingsCount.text = "${product.rating.count} ratings"
        productDetailBinding.ratingRank.text = ratingRank

        Picasso.get()
            .load(product.image)
            .placeholder(R.drawable.shoeimg)
            .into(productDetailBinding.detailImageView)
    }
}