package com.turanpay.account.dto;

public record AuthRequest(
        String username,
        String password
) {
}
