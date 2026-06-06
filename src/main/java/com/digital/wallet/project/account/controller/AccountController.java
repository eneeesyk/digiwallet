package com.digital.wallet.project.account.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.digital.wallet.project.account.domain.Money;
import com.digital.wallet.project.account.application.services.AccountService;
import com.digital.wallet.project.account.domain.Account;

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
    public ResponseEntity<Void> openAccount(@PathVariable Long accountId, @RequestParam BigDecimal initialBalance,@RequestParam String currency){
        accountService.openAccount(accountId, new Money(initialBalance, currency));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount, @RequestParam String currency){
        accountService.deposit(accountId, new Money(amount, currency));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long accountId, @RequestParam BigDecimal amount, @RequestParam String currency){
        accountService.withdraw(accountId, new Money(amount, currency));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Money> getBalance(@PathVariable Long accountId){
        Account account = accountService.replayEvents(accountId);
        return ResponseEntity.ok(account.getBalance());
    }
}
