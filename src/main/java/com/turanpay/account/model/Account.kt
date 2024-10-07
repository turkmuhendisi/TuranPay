package com.turanpay.account.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashSet

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,
    val accountNumber: String?,
    val IBAN: String?,
    var balance: BigDecimal? = BigDecimal.ZERO,
    val creationDate: LocalDateTime?,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "customer_id", nullable = false)
    val customer: Customer?,

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val transaction: Set<Transaction> = HashSet()
) {
    constructor(accountNumber: String, iban: String, customer: Customer, balance: BigDecimal, creationDate: LocalDateTime) : this(
        null,
        accountNumber = accountNumber,
        IBAN = iban,
        customer = customer,
        balance = balance,
        creationDate = creationDate
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (balance != other.balance) return false
        if (creationDate != other.creationDate) return false
        if (customer != other.customer) return false
        if (transaction != other.transaction) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + (creationDate?.hashCode() ?: 0)
        result = 31 * result + (customer?.hashCode() ?: 0)
        return result
    }
}
