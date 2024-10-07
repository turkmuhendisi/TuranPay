package com.turanpay.account.dto

import jakarta.validation.constraints.NotBlank

data class CreateAccountRequest(
    @field:NotBlank
    val customerId: String
)
