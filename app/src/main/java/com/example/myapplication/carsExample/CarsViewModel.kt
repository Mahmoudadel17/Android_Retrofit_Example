package com.example.myapplication.carsExample

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarsViewModel :ViewModel(){

    var state by mutableStateOf(emptyList<Product>())

    private var apiService: CarApiService
    private lateinit var carsCall: Call<List<Product>>

    init{
        val retrofit:Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            ).baseUrl(
                "https://cars-links-8a4e2-default-rtdb.firebaseio.com/"
            ).build()

        apiService = retrofit.create(CarApiService::class.java)


//        getCars()
    }

    fun getCars(context:Context){

        carsCall = apiService.getCarLinks()
        carsCall.enqueue(object :Callback<List<Product>>{
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                response.body()?.let {
                  Toast.makeText(context,"hello ${it.size}   TITLE: ${it[0].title}",Toast.LENGTH_SHORT).show()
                   state = it
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }

        })


    }


    override fun onCleared() {
        super.onCleared()
        carsCall.cancel()
    }

    fun buyStateCallBack(product: Product){

    }
}