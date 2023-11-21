package com.example.ecommerceapp.model

import java.io.Serializable

data class ProductListItem(
    val id: Int,
    val title: String,
    val price: Float,
    val category: String,
    val description: String,
    val image: String,
    val rating: Rating
)

data class Rating(
    val rate: Float,
    val count: String
)
