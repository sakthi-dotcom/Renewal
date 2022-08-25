package com.example.ssldomainmaintenance.activity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface sslInterface {

    @GET("api/SSL/GetAllSSL")
    fun getData():Call<List<sslDataItem>>

}