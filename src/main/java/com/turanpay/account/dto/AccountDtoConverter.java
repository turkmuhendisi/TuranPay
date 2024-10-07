package com.turanpay.account.dto;

import com.turanpay.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AccountDtoConverter {
    private final CustomerDtoConverter customerDtoConverter;
    private final TransactionDtoConverter transactionDtoConverter;

    public AccountDtoConverter(CustomerDtoConverter customerDtoConverter, TransactionDtoConverter transactionDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;
        this.transactionDtoConverter = transactionDtoConverter;
    }

    public AccountDto convert(Account from) {
        return new AccountDto(
                from.getId(),
                from.getAccountNumber(),
                from.getIBAN(),
                from.getBalance(),
                Objects.requireNonNull(from.getCreationDate()),
                customerDtoConverter.convertToAccountCustomerDto(Objects.requireNonNull(from.getCustomer())),
                from.getTransaction().stream().map(transactionDtoConverter::convert).collect(Collectors.toSet()));
    }
}
