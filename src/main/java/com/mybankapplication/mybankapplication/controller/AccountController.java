package com.mybankapplication.mybankapplication.controller;

import com.mybankapplication.mybankapplication.dto.AccountDto;
import com.mybankapplication.mybankapplication.dto.CreateAccountRequest;
import com.mybankapplication.mybankapplication.dto.CustomerDto;
import com.mybankapplication.mybankapplication.dto.UpdateAccountRequest;
import com.mybankapplication.mybankapplication.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getALlAccounts(){
       return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String id){
        return ResponseEntity.ok(accountService.getAccountById( id ));

    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest){
        return ResponseEntity.ok(accountService.createAccount( createAccountRequest ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String id ,
                                                    @RequestBody UpdateAccountRequest updateAccountRequest){
        return ResponseEntity.ok(accountService.updateAccount(id , updateAccountRequest));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id){
        accountService.deleteAccount( id );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<AccountDto> withdrawMoney(@PathVariable String id , @PathVariable Double amount){
        return ResponseEntity.ok(accountService.withdrawMoney(id , amount));
    }

    @PutMapping("/add/{id}/{amount}")
    public ResponseEntity<AccountDto> addMoney(@PathVariable String id , @PathVariable Double amount){
        return ResponseEntity.ok(accountService.addMoney(id , amount));
    }



}
