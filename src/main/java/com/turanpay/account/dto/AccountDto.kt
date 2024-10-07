package com.turanpay.account.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class AccountDto(
    val id: UUID?,
    val accountNumber: String?,
    val IBAN: String?,
    val balance: BigDecimal? = BigDecimal.ZERO,
    val creationDate: LocalDateTime,
    val customer: AccountCustomerDto?,
    val transactions: MutableSet<TransactionDto>?
)
