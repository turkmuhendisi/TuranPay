package com.turanpay.account.dto;

import com.turanpay.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {
    private final CustomerAccountDtoConverter converter;

    public CustomerDtoConverter(CustomerAccountDtoConverter converter) {
        this.converter = converter;
    }

    public AccountCustomerDto convertToAccountCustomerDto(Customer from) {
        return new AccountCustomerDto(
                Objects.requireNonNull(from.getId()),
                Objects.requireNonNull(from.getName()),
                Objects.requireNonNull(from.getSurname()));
    }

    public CustomerDto convertToCustomerDto(Customer from) {
        return new CustomerDto(
                Objects.requireNonNull(from.getId()),
                Objects.requireNonNull(from.getName()),
                Objects.requireNonNull(from.getSurname()),
                Objects.requireNonNull(from.getEmail()),
                Objects.requireNonNull(from.getPhone()),
                Objects.requireNonNull(from.getAccounts()).stream().map(converter::convert).collect(Collectors.toSet()));
    }
}
