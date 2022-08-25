package com.example.ssldomainmaintenance.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("api/User/LoginAsUser")
    fun userLogin(@Body dataModel:LoginData?): Call<loginResponse>
}