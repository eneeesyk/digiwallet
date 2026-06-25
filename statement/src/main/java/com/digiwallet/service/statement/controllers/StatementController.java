package com.digiwallet.service.statement.controllers;

import java.math.BigDecimal;
import java.util.List;

import com.digiwallet.service.statement.entities.TransactionRecord;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digiwallet.service.statement.services.StatementService;

@RestController
@RequestMapping("/statements")
public class StatementController {

    private final StatementService statementService;

    public StatementController(StatementService statementService){
        this.statementService = statementService;
    }
    

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId){
        return ResponseEntity.ok(statementService.getBalance(accountId));
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionRecord>> getTransactions(Long accountId){
        return ResponseEntity.ok(statementService.getTransactions(accountId));
    }
}
