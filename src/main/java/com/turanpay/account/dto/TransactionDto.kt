package com.turanpay.account.dto

import com.turanpay.account.model.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionDto(
    val id: String?,
    val transactionType: TransactionType?,
    val amount: BigDecimal? = BigDecimal.ZERO,
    val transactionDate: LocalDateTime?
)
