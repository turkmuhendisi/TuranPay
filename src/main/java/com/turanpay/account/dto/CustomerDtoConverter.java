package com.turanpay.account.dto;

import com.turanpay.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {
    private final CustomerAccountDtoConverter converter;

    public CustomerDtoConverter(CustomerAccountDtoConverter converter) {
        this.converter = converter;
    }

    public AccountCustomerDto convertToAccountCustomerDto(Customer from) {
        return new AccountCustomerDto(
                from.getId(),
                from.getName(),
                from.getSurname());
    }

    public CustomerDto convertToCustomerDto(Customer from) {
        return new CustomerDto(
                from.getId(),
                from.getName(),
                from.getSurname(),
                from.getEmail(),
                from.getPhone(),
                from.getAccounts().stream().map(converter::convert).collect(Collectors.toSet()));
    }
}
