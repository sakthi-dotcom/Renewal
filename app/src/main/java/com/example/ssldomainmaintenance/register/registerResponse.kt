package com.example.ssldomainmaintenance.register

data class registerResponse (val statusCode:Int,
                             val result:String,
                             val hasError:Boolean,
                             val message:String,
                             val requestTime:String)