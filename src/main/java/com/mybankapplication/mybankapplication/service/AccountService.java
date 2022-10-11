package com.mybankapplication.mybankapplication.service;

import com.mybankapplication.mybankapplication.dto.AccountDto;
import com.mybankapplication.mybankapplication.dto.AccountDtoConverter;
import com.mybankapplication.mybankapplication.dto.CreateAccountRequest;
import com.mybankapplication.mybankapplication.dto.UpdateAccountRequest;
import com.mybankapplication.mybankapplication.exception.CustomerNotFoundException;
import com.mybankapplication.mybankapplication.model.Account;
import com.mybankapplication.mybankapplication.model.Customer;
import com.mybankapplication.mybankapplication.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {



    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountDtoConverter accountDtoConverter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){

        Customer customer = customerService.getCustomerById(createAccountRequest.getCustomerId());
        if(customer.getId() == null || customer.getId().trim().equals("")){
             throw new CustomerNotFoundException("Customer Not found!");
        }

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();
        return accountDtoConverter.convert(accountRepository.save(account));
    }

    public AccountDto updateAccount(String id , UpdateAccountRequest updateAccountRequest){
        Customer customer = customerService.getCustomerById(updateAccountRequest.getCustomerId());
        if(customer.getId().equals("")|| customer.getId().trim().equals("")){
            return AccountDto.builder().build();
        }

        Optional<Account> accountOptional = accountRepository.findById( id );
        accountOptional.ifPresent(account -> {
            account.setBalance(updateAccountRequest.getBalance());
            account.setCity((updateAccountRequest.getCity()));
            account.setCurrency(updateAccountRequest.getCurrency());
            account.setCustomerId((updateAccountRequest.getCustomerId()));
            accountRepository.save( account );
        });

        return accountOptional.map(accountDtoConverter :: convert).orElse(new AccountDto());
    }


    public List<AccountDto> getAllAccounts(){
        List<Account> accountList = accountRepository.findAll();

        return accountList.stream().map(accountDtoConverter :: convert).collect(Collectors.toList());

    }

    public AccountDto getAccountById(String id){
        return accountRepository.findById( id )
                .map(accountDtoConverter::convert)
                .orElse(new AccountDto());
    }

    public void deleteAccount(String id) {
        accountRepository.deleteById( id );
    }

    public AccountDto withdrawMoney(String id , Double amount){

        Optional<Account> accountOptional = accountRepository.findById( id );
        accountOptional.ifPresent(account -> {
            if(account.getBalance() > amount){
                account.setBalance(account.getBalance() - amount);
                accountRepository.save( account );
            }
            else{
                System.out.println("Unsufficient funds -> accountId : " +id + "balance : " + account.getBalance() + "amount : " + amount);
            }
        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }


    public AccountDto addMoney(String id , Double amount){

        Optional<Account> accountOptional = accountRepository.findById( id );
        accountOptional.ifPresent(account -> {
                account.setBalance(account.getBalance() + amount);
                accountRepository.save( account );

        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }
}
