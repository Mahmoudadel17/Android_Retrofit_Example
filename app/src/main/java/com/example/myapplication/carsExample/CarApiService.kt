package com.example.myapplication.carsExample

import retrofit2.Call
import retrofit2.http.GET

interface CarApiService {
    @GET("links.json")
    fun getCarLinks(): Call<List<Product>>
}