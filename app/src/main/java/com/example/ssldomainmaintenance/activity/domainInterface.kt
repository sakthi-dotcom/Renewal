package com.example.ssldomainmaintenance.activity

import retrofit2.Call
import retrofit2.http.GET

interface domainInterface {

    @GET("api/Domain/GetAllDomains")
    fun getdata(): Call<List<domainDataItem>>
}