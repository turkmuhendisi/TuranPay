package com.turanpay.account.service;

import com.turanpay.account.dto.CreateCustomerRequest;
import com.turanpay.account.dto.CustomerDto;
import com.turanpay.account.dto.CustomerDtoConverter;
import com.turanpay.account.exception.CustomerNotFoundException;
import com.turanpay.account.model.Customer;
import com.turanpay.account.model.MyUserPrincipal;
import com.turanpay.account.repository.CustomerRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter converter;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter converter, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    protected Customer findCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer could not found by id " + id));
    }

    public CustomerDto getCustomerById(String customerId) {
        return converter.convertToCustomerDto(findCustomerById(customerId));
    }

    public CustomerDto findUserByJwt(String token) {
        String email = jwtService.extractUser(token);
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return converter.convertToCustomerDto(customer);
    }

    public Customer createCustomer(CreateCustomerRequest from) {
        Customer customer = new Customer(
                from.getName(),
                from.getSurname(),
                from.getEmail(),
                from.getPhone(),
                passwordEncoder.encode(from.getPassword()),
                LocalDateTime.now());
        return customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByEmail(username);
        Customer customer = customerOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new MyUserPrincipal(customer);
    }
}
