package com.turanpay.account.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
data class BiometricData(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var faceId: UUID? = null,

    val faceImage: String,

    @Column(name = "customer_id", nullable = false)
    val customerId: String
) {
    constructor(faceImage: String, customerId: String) : this(
        faceId = null,
        faceImage = faceImage,
        customerId = customerId
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BiometricData

        if (faceId != other.faceId) return false
        if (faceImage != other.faceImage) return false
        if (customerId != other.customerId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = faceId.hashCode()
        result = 31 * result + faceImage.hashCode()
        result = 31 * result + customerId.hashCode()
        return result
    }

}
