package com.turanpay.account.repository;

import com.turanpay.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByCustomerId(UUID customer_id);
    Account findAccountByIBAN(String iban);
}
