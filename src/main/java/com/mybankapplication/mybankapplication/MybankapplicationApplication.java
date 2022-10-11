package com.mybankapplication.mybankapplication;

import com.mybankapplication.mybankapplication.model.Account;
import com.mybankapplication.mybankapplication.model.City;
import com.mybankapplication.mybankapplication.model.Customer;
import com.mybankapplication.mybankapplication.repository.AccountRepository;
import com.mybankapplication.mybankapplication.repository.CustomerRepository;
import com.mybankapplication.mybankapplication.service.AccountService;
import com.mybankapplication.mybankapplication.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class MybankapplicationApplication implements CommandLineRunner {

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public MybankapplicationApplication(AccountService accountService, AccountRepository accountRepository, CustomerService customerService, CustomerRepository customerRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MybankapplicationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Customer c1 = Customer.builder()
				.id("12345")
				.name("Mehmet")
				.city(City.ANTALYA)
				.address("Ev")
				.dateOfBirth(1994)
				.build();
		Customer c2 = Customer.builder()
				.id("678910")
				.name("Oğulcan")
				.city(City.GAZIANTEP)
				.address("Ev")
				.dateOfBirth(1988)
				.build();
		Customer c3 = Customer.builder()
				.id("2322323")
				.name("Furkan")
				.city(City.ELAZIG)
				.address("İş")
				.dateOfBirth(1996)
				.build();


		customerRepository.saveAll(Arrays.asList(c1 , c2 , c3));

		Account a1 = Account.builder()
				.id("100")
				.customerId("123456")
				.city(City.ANTALYA)
				.balance(1320.0)
				.build();
		Account a2 = Account.builder()
				.id("101")
				.customerId("67895")
				.city(City.GAZIANTEP)
				.balance(6789.0)
				.build();
		Account a3 = Account.builder()
				.id("102")
				.customerId("567812")
				.city(City.ELAZIG)
				.balance(120000.0)
				.build();


		accountRepository.saveAll(Arrays.asList(a1 ,a2 ,a3));

	}
}
