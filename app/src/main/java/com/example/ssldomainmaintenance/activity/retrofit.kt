package com.example.ssldomainmaintenance.activity

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofit {


    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.172:5001/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}