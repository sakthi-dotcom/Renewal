package com.example.ssldomainmaintenance.activity

import java.io.Serializable

data class domainDataItem(
    val auto_renewal_enabled: Boolean,
    val domain_name: String,
    val expires_on: Long,
    val issued_by: String,
    val issued_on: Long,
    val issued_to: String
):Serializable