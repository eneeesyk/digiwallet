package com.digital.wallet.project.account.infrastructure.entities;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_balances")
public class AccountBalanceEntity {

    @Id
    private Long accountId;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    public AccountBalanceEntity() {}

    public AccountBalanceEntity(Long accountId, BigDecimal balance, String currency, Instant lastUpdated) {
        this.accountId = accountId;
        this.balance = balance;
        this.currency = currency;
        this.lastUpdated = lastUpdated;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    
}
