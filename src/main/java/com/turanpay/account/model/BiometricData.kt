package com.turanpay.account.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@Entity
data class BiometricData(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val faceId: String,

    @Lob
    @Column(length = 16777215)
    val faceImage: String, // base64 encoded image string

    @Column(name = "customer_id", nullable = false)
    val customerId: String
) {
    constructor(faceImage: String, customerId: String) : this(
        faceId = "",
        faceImage = faceImage,
        customerId = customerId
    )
}
