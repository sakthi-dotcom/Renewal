package com.example.ssldomainmaintenance.activity

import java.io.Serializable

data class sslRestoreItem(
    val name: String,
    val issued_by: String,
    val issued_to: String,
    val issued_on: Long,
    val expires_on: Long,
    val certificate_type:String,
    val auto_renewal_enabled: Boolean
):Serializable
