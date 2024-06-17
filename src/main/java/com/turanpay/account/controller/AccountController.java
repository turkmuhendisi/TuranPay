package com.turanpay.account.controller;

import com.turanpay.account.dto.AccountDto;
import com.turanpay.account.dto.CreateAccountRequest;
import com.turanpay.account.service.AccountService;
import com.turanpay.account.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/auth")
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @PostMapping("/deposit")
    public void depositAccount(@RequestBody String customerId, BigDecimal amount) {
        transactionService.deposit(customerId, amount);
    }

    @PostMapping("/withdraw")
    public void withdrawAccount(@RequestBody String customerId, BigDecimal amount) {
        transactionService.withdraw(customerId, amount);
    }

    @GetMapping("/iban")
    public String getIban() {
        //String accountNumber = generateAccountNumber();
        //return "IBAN: " + generateIban(accountNumber) + "\nAccount Number: " + accountNumber;
        return null;
    }
}
