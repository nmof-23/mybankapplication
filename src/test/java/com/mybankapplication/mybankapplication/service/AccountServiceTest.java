package com.mybankapplication.mybankapplication.service;

import com.mybankapplication.mybankapplication.dto.AccountDto;
import com.mybankapplication.mybankapplication.dto.AccountDtoConverter;
import com.mybankapplication.mybankapplication.dto.CreateAccountRequest;
import com.mybankapplication.mybankapplication.model.Account;
import com.mybankapplication.mybankapplication.model.City;
import com.mybankapplication.mybankapplication.model.Currency;
import com.mybankapplication.mybankapplication.model.Customer;
import com.mybankapplication.mybankapplication.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private AccountService accountService;

    private  AccountRepository accountRepository;
    private  CustomerService customerService;
    private  AccountDtoConverter accountDtoConverter;

    @Before
    public void setUp() throws Exception {

        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);

        accountService = new AccountService(accountRepository , customerService , accountDtoConverter);

    }

    @Test
    public void whenCreateAccountWithValidRequest_itShouldReturnValidAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");

        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ELAZIG);
        createAccountRequest.setCurrency(Currency.EUR);

        Customer customer = Customer.builder()
                .id("12345")
                .address("Ev")
                .dateOfBirth(1994)
                .city(City.ELAZIG)
                .name("Mehmet")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        AccountDto accountDto = AccountDto.builder()
                .id("1234")
                .customerId("12345")
                .currency(Currency.EUR)
                .balance(100.0)
                .build();


        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer) ;
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result ,accountDto);

        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);

    }


    @Test(expected = RuntimeException.class)
    public void whenCreateAccountWithNonExistCustomer_itShouldReturnEmptyAccountDto(){

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");

        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ELAZIG);
        createAccountRequest.setCurrency(Currency.EUR);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder().build()) ;

        AccountDto expectedAccountDto = AccountDto.builder().build();
        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result , expectedAccountDto);
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);


    }


    @Test(expected = RuntimeException.class)
    public void whenCreateAccountWithCustomerWithOutId_itShouldReturnEmptyAccountDto(){

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");

        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ELAZIG);
        createAccountRequest.setCurrency(Currency.EUR);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder()
                .id(" ")
                .build());

        AccountDto expectedAccountDto = AccountDto.builder().build();
        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result , expectedAccountDto);
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);


    }


    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledAndRepositoryThrewException_itShouldThrowException(){

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");

        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ELAZIG);
        createAccountRequest.setCurrency(Currency.EUR);

        Customer customer = Customer.builder()
                .id("12345")
                .address("Ev")
                .dateOfBirth(1994)
                .city(City.ELAZIG)
                .name("Mehmet")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        AccountDto accountDto = AccountDto.builder()
                .id("1234")
                .customerId("12345")
                .currency(Currency.EUR)
                .balance(100.0)
                .build();


        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer) ;
        Mockito.when(accountRepository.save(account)).thenThrow(new RuntimeException("bla bla"));

        accountService.createAccount(createAccountRequest);



        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verifyNoInteractions(accountDtoConverter);


    }







}