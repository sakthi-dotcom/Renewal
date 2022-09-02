package com.example.ssldomainmaintenance.activity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface delete {

    @GET("/api/Domain/GetSoftDeleted")
    fun getdata(): Call<List<restoreItem>>

    @PUT("/api/Domain/RestoreSoftDeleted")
    fun modify(@Query("DOMAIN_NAME") Name: String):Call<domainDelete>

    @GET("/api/SSL/GetSoftDeleted")
    fun getData1():Call<List<sslRestoreItem>>

    @PUT("/api/SSL/RestoreSoftDeleted")
    fun modify_ssl(@Query("DOMAIN_NAME") Name: String):Call<domainDelete>
}