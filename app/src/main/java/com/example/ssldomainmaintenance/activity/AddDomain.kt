package com.example.ssldomainmaintenance.activity

import retrofit2.Call
import retrofit2.http.*

interface AddDomain {
    @POST("api/Domain/AddNewDomain")
    fun  createPost(@Body dataModel: AddDomainDataModel?): Call<AddDomainDataModel?>?


    @PUT("api/Domain/UpdateDates")
    fun updateDate(@Query("DOMAIN_NAME") Name: String,
                   @Query("ISSUED_ON") issued_on:Long,
                   @Query("EXPIRES_ON")expires_on:Long):Call<update>

    @DELETE("api/Domain/SoftDeleteDomain")
    fun deleteDomain(@Query("DOMAIN_NAME") Name: String):Call<domainDelete>

    @DELETE("api/Domain/HardDeleteDomain")
    fun domain_hard(@Query("DOMAIN_NAME") Name: String):Call<domainDelete>


}