package com.turanpay.account.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Sequence(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val type: String,
    var lastValue: Long
) {
    constructor(accountNumber: String, lastValue: Long) : this(
        type = accountNumber,
        lastValue = lastValue
    )
}
