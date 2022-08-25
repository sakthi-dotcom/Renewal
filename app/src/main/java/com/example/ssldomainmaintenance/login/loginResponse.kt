package com.example.ssldomainmaintenance.login

data class loginResponse(
    val statusCode:Int,
    val result:String,
    val hasError:Boolean,
    val message:String,
    val requestTime:String
)