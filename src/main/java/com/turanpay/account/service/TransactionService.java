package com.turanpay.account.service;

import com.turanpay.account.dto.CustomerDto;
import com.turanpay.account.model.Account;
import com.turanpay.account.model.Customer;
import com.turanpay.account.model.Transaction;
import com.turanpay.account.repository.AccountRepository;
import com.turanpay.account.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CustomerService customerService;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService, CustomerService customerService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.customerService = customerService;
    }

    public void deposit(String customerId, BigDecimal amount) {
        CustomerDto customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with ID " + customerId);
        }

        Account account = accountRepository.findAccountByCustomerId(customer.getId());
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = Objects.requireNonNull(currentBalance).add(amount);

        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    public void withdraw(String customerId, BigDecimal amount) {
        CustomerDto customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with ID " + customerId);
        }

        Account account = accountRepository.findAccountByCustomerId(customer.getId());
        BigDecimal currentBalance = account.getBalance();
        if (Objects.requireNonNull(currentBalance).compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance in account for customer ID " + customerId);
        }

        BigDecimal newBalance = currentBalance.subtract(amount);

        account.setBalance(newBalance);
        accountRepository.save(account);
    }
    public void transferMoney(String iban, BigDecimal amount, String token) throws Exception {
        if (amount.compareTo(BigDecimal.valueOf(5)) > 0) {
            // Receiver
            Account receiverAccount = accountService.getAccountByIban(iban);
            BigDecimal currentBalance = receiverAccount.getBalance();
            BigDecimal newBalance = Objects.requireNonNull(currentBalance).add(amount);
            receiverAccount.setBalance(newBalance);

            accountRepository.save(receiverAccount);

            // Sender
            CustomerDto customer = customerService.findUserByJwt(token);
            Account senderAccount = accountService.getAccountByCustomerId(customer.getId());

            // From account

            Transaction transaction = new Transaction(
                    newBalance,
                    Objects.requireNonNull(receiverAccount.getId()),
                    senderAccount);

            transactionRepository.save(transaction);

        } else throw new Exception("Amount should be higher than 5₺");
    }
}
