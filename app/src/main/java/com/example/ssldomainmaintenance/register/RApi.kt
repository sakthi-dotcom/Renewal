package com.example.ssldomainmaintenance.register

import com.example.ssldomainmaintenance.login.LoginData
import com.example.ssldomainmaintenance.login.loginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RApi {

    @POST("api/User/AddNewUser")
    fun userRegister(@Body dataModel: RegisterData?): Call<registerResponse>
}