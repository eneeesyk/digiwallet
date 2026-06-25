package com.digiwallet.service.statement.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digiwallet.service.statement.entities.AccountProjection;
import com.digiwallet.service.statement.entities.TransactionRecord;
import com.digiwallet.service.statement.repositories.AccountProjectionRepository;
import com.digiwallet.service.statement.repositories.TransactionRecordRepository;

@Service
public class StatementService {

    private final AccountProjectionRepository accountProjectionRepository;

    private final TransactionRecordRepository transactionRecordRepository;

    public StatementService(AccountProjectionRepository accountProjectionRepository, TransactionRecordRepository transactionRecordRepository){
        this.transactionRecordRepository = transactionRecordRepository;
        this.accountProjectionRepository = accountProjectionRepository;
    }

    public BigDecimal getBalance(Long accountId){
        AccountProjection account = accountProjectionRepository.findByAccountId(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found: " + accountId);
        }
        return account.getBalance();
    }

    public List<TransactionRecord> getTransactions(Long accountId){
        List<TransactionRecord> transactionRecords = transactionRecordRepository.findByAccountIdOrderByOccurredAtAsc(accountId);
        return transactionRecords;
    }
    
}
