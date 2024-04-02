package com.turanpay.account.dto

data class CreateCustomerRequest(
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val password: String
)
