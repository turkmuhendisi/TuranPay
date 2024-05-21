package com.turanpay.account.controller;

import com.turanpay.account.dto.AuthRequest;
import com.turanpay.account.dto.CreateCustomerRequest;
import com.turanpay.account.dto.CustomerDto;
import com.turanpay.account.model.Customer;
import com.turanpay.account.service.CustomerService;
import com.turanpay.account.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class CustomerController {
    private final CustomerService customerService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public CustomerController(CustomerService customerService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.customerService = customerService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/customer-register")
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.createCustomer(request);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

    @GetMapping("/profile")
    public CustomerDto getUsername(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return customerService.findUserByJwt(token);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping("/welcome")
    public String getString() {
        return "Welcome Spring Security";
    }

    @GetMapping("/user")
    public String getUserString() {
        return "This is User";
    }

    @GetMapping("/admin")
    public String getAdminString() {
        return "This is Admin";
    }
}
