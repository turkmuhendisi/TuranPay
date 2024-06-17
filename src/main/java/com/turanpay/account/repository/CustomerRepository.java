package com.turanpay.account.repository;

import com.turanpay.account.dto.CustomerDto;
import com.turanpay.account.model.Customer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
}
