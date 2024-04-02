package com.turanpay.account.controller;

import com.turanpay.account.dto.CreateCustomerRequest;
import com.turanpay.account.dto.CustomerDto;
import com.turanpay.account.model.Customer;
import com.turanpay.account.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.createCustomer(request);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }
}
