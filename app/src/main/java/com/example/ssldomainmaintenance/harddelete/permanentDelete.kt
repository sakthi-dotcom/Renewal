package com.example.ssldomainmaintenance.harddelete

import com.example.ssldomainmaintenance.activity.domainDelete
import com.example.ssldomainmaintenance.activity.sslDelete
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Query

interface permanentDelete {

   @DELETE("api/Domain/HardDeleteDomain")
   fun domain_hard(@Query("DOMAIN_NAME") Name: String):Call<domainDelete>

   @DELETE("/api/SSL/HardDeleteSSL")
   fun ssl_hard(@Query("NAME") Name: String):Call<sslDelete>


}