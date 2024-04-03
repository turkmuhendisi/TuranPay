package com.turanpay.account.service;

import com.turanpay.account.dto.AccountDto;
import com.turanpay.account.dto.AccountDtoConverter;
import com.turanpay.account.dto.CreateAccountRequest;
import com.turanpay.account.model.Account;
import com.turanpay.account.model.Customer;
import com.turanpay.account.model.Transaction;
import com.turanpay.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter converter;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.converter = converter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());
        Account account = new Account(
                customer,
                BigDecimal.ZERO,
                LocalDateTime.now());

        //if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(BigDecimal.ZERO, account);
            account.getTransaction().add(transaction);
        //}

        return converter.convert(accountRepository.save(account));
    }

}
