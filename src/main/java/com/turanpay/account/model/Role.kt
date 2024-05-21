package com.turanpay.account.model

import org.springframework.security.core.GrantedAuthority

enum class Role(val value: String) :GrantedAuthority {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    override fun getAuthority(): String {
        return name
    }
}
