package com.turanpay.account.dto

data class CustomerDto(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val accounts: Set<CustomerAccountDto>
)
