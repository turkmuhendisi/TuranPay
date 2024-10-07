package com.turanpay.account.service;

import com.turanpay.account.dto.AccountDto;
import com.turanpay.account.dto.AccountDtoConverter;
import com.turanpay.account.dto.CreateAccountRequest;
import com.turanpay.account.model.Account;
import com.turanpay.account.model.Customer;
import com.turanpay.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter converter;
    private final AccountNumberGenerator accountNumberGenerator;
    private final IBANGenerator ibanGenerator;
    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter converter,
                          AccountNumberGenerator accountNumberGenerator,
                          IBANGenerator ibanGenerator) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.converter = converter;
        this.accountNumberGenerator = accountNumberGenerator;
        this.ibanGenerator = ibanGenerator;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());
        String accountNumber = accountNumberGenerator.generateAccountNumber();
        String iban = ibanGenerator.generateIban(accountNumber);
        Account account = new Account(
                accountNumber,
                iban,
                customer,
                BigDecimal.ZERO,
                LocalDateTime.now());

        //if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
        //Transaction transaction = new Transaction(BigDecimal.ZERO, account);
        //account.getTransaction().add(transaction);
        //}

        return converter.convert(accountRepository.save(account));
    }

    public Account getAccountByIban(String iban) {
        return accountRepository.findAccountByIBAN(iban);
    }

    public Account getAccountByCustomerId(String customerId) {
        return accountRepository.findAccountByCustomerId(UUID.fromString(customerId));
    }
}
