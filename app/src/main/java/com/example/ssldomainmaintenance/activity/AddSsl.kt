package com.example.ssldomainmaintenance.activity

import retrofit2.Call
import retrofit2.http.*

interface AddSsl {

    @POST("api/SSL/AddNewSSL")
    fun  createPost1(@Body dataModelSsl: AddSslDataModel?): Call<AddSslDataModel?>?


    @PUT("api/SSL/UpdateDates")
    fun updateDate1(@Query("NAME") Name: String,
                   @Query("ISSUED_ON") issued_on:Long,
                   @Query("EXPIRES_ON")expires_on:Long):Call<update1>

    @DELETE("api/SSL/HardDeleteSSL")
    fun deleteSsl(@Query("NAME") Name: String):Call<sslDelete>
}