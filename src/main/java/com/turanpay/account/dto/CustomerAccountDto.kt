package com.turanpay.account.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class CustomerAccountDto(
    val id: UUID,
    val accountNumber: String,
    val IBAN: String,
    val balance: BigDecimal? = BigDecimal.ZERO,
    val transactions: MutableSet<TransactionDto>?,
    val creationDate: LocalDateTime
)
