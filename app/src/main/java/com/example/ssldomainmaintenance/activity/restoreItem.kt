package com.example.ssldomainmaintenance.activity

import java.io.Serializable


data class restoreItem(
    val domain_name: String,
    val issued_by: String,
    val issued_to: String,
    val issued_on: Long,
    val expires_on: Long,
    val auto_renewal_enabled: Boolean
): Serializable
