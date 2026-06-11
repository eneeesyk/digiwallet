package com.digital.wallet.project.account.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.digital.wallet.project.account.domain.Money;
import com.digital.wallet.project.account.application.services.AccountService;
import com.digital.wallet.project.account.objects.AccountId;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping("/{accountId}/open")
    public ResponseEntity<Void> openAccount(@PathVariable("accountId") Long accountId, @RequestParam("initialBalance") BigDecimal initialBalance, @RequestParam("currency") String currency){
        accountService.openAccount(new AccountId(accountId), new Money(initialBalance, currency));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable("accountId") Long accountId, @RequestParam("amount") BigDecimal amount, @RequestParam("currency") String currency){
        accountService.deposit(new AccountId(accountId), new Money(amount, currency));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable("accountId") Long accountId, @RequestParam("amount") BigDecimal amount, @RequestParam("currency") String currency){
        accountService.withdraw(new AccountId(accountId), new Money(amount, currency));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Money> getBalance(@PathVariable("accountId") Long accountId){
        return ResponseEntity.ok(accountService.getBalance(new AccountId(accountId)));
    }
}
