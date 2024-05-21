package com.turanpay.account.dto

import com.turanpay.account.model.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime

data class CustomerAccountDto(
    val id: String,
    val accountNumber: String,
    val IBAN: String,
    val balance: BigDecimal? = BigDecimal.ZERO,
    val transactions: Set<TransactionDto>?,
    val creationDate: LocalDateTime
)
