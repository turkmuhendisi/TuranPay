package com.turanpay.account.dto

import java.util.*

data class CustomerDto(
    val id: UUID,
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val accounts: MutableSet<CustomerAccountDto>
)
