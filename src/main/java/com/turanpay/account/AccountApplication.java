package com.turanpay.account;

import com.turanpay.account.model.Customer;
import com.turanpay.account.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AccountApplication implements CommandLineRunner {
	/*private CustomerRepository customerRepository;

    public AccountApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }*/

    public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Test Customer
		/*Customer customer = customerRepository.save(
				new Customer("",
						"Berat",
						"Koca",
						"berkatkoca@mail.com",
						"05556549878",
						"password123!",
						new HashSet<>()));
		System.out.println(customer);*/
	}
}
