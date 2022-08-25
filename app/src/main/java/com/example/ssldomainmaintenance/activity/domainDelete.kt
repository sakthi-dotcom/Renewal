package com.example.ssldomainmaintenance.activity

data class domainDelete(
    var result : String? = null,
    var hasError : Boolean? = null,
    var message : String? = null,
    var requestTime : String? = null,
    var statusCode : Int? = null,
)
