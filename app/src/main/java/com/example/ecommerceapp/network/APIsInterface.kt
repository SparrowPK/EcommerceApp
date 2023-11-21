package com.example.ecommerceapp.network

import retrofit2.Call
import com.example.ecommerceapp.model.ProductList
import com.example.ecommerceapp.model.ProductListItem
import retrofit2.http.GET
import retrofit2.http.Query

interface APIsInterface {
    @GET("/products")
    fun getAllProducts(): Call<ProductList>

    @GET("/products")
    fun fetchProduct(@Query("id") id: Int): Call<ProductListItem>
}
