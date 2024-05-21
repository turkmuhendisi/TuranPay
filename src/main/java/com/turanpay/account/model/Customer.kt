package com.turanpay.account.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime

@Entity
data class Customer(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String?,
    val name: String?,
    val surname: String?,
    val email: String?,
    val phone: String?,
    val password: String?,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    val authorities: Set<Role>?,

    val creationDate: LocalDateTime?,

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    val accounts: Set<Account>? = HashSet()
) {

    constructor(name: String, surname: String, email: String, phone: String, password: String, creationDate: LocalDateTime) : this(
        "",
        name,
        surname,
        email,
        phone,
        password,
        authorities = setOf(Role.ROLE_USER),
        creationDate = creationDate
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (email != other.email) return false
        if (phone != other.phone) return false
        if (password != other.password) return false
        if (authorities != other.authorities) return false
        if (accounts != other.accounts) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (surname?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (authorities?.hashCode() ?: 0)
        return result
    }
}




