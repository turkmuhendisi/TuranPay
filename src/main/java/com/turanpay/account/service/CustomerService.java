package com.turanpay.account.service;

import com.turanpay.account.dto.CreateCustomerRequest;
import com.turanpay.account.dto.CustomerDto;
import com.turanpay.account.dto.CustomerDtoConverter;
import com.turanpay.account.exception.CustomerNotFoundException;
import com.turanpay.account.model.Customer;
import com.turanpay.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter converter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter converter) {
        this.customerRepository = customerRepository;
        this.converter = converter;
    }

    protected Customer findCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer could not found by id " + id));
    }

    public CustomerDto getCustomerById(String customerId) {
        return converter.convertToCustomerDto(findCustomerById(customerId));
    }

    public Customer createCustomer(CreateCustomerRequest from) {
        Customer customer = new Customer(
                from.getName(),
                from.getSurname(),
                from.getEmail(),
                from.getPhone(),
                from.getPassword()
                );
        return customerRepository.save(customer);
    }
}
