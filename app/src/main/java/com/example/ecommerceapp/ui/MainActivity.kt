package com.example.ecommerceapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceapp.adapter.ProductAdapter
import com.example.ecommerceapp.databinding.ActivityMainBinding
import com.example.ecommerceapp.databinding.ToolBarBinding
import com.example.ecommerceapp.model.ProductList
import com.example.ecommerceapp.network.APIClient
import com.example.ecommerceapp.network.APIsInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private var apiService : APIsInterface = APIClient.getIntance().create(APIsInterface::class.java)
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarBinding: ToolBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

//        toolbarBinding = mainBinding.homepageToolbar
//        setSupportActionBar(toolbar)
        listProducts()
    }

    private fun listProducts(){
        apiService.getAllProducts().enqueue(object: Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                if(response.isSuccessful) {
                    val products = response.body()
                    if (products != null){
                        productAdapter = ProductAdapter(products)
                        mainBinding.recyclerView.adapter = productAdapter
                    } else {
                        Log.e("on Response error", response.errorBody().toString())
                    }
                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                call.cancel()
                Log.e("on failure error", t.message.toString())
            }

        })
    }
}
