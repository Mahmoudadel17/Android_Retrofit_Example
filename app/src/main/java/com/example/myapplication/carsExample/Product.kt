package com.example.myapplication.carsExample

import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("link")
    val url:String,

    @SerializedName("id")
    val id:Int,

    val title:String = "Car",
)
