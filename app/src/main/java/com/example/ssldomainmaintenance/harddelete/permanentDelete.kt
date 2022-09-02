package com.example.ssldomainmaintenance.harddelete

import com.example.ssldomainmaintenance.activity.domainDelete
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Query

interface permanentDelete {

   @DELETE("api/Domain/HardDeleteDomain")
   fun domain_hard(@Query("NAME") Name: String):Call<domainDelete>

}