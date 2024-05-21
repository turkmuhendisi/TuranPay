package com.turanpay.account.repository;

import com.turanpay.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByCustomerId(String customerId);
    Account findAccountByIBAN(String iban);
}
