package com.digiwallet.service.transfer.pojos;

import java.math.BigDecimal;

public class TransferRequest {

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal amount;

    private String currency;

    public TransferRequest() {
    }

    public TransferRequest(Long toAccountId, Long fromAccountId, BigDecimal amount, String currency) {
        this.toAccountId = toAccountId;
        this.fromAccountId = fromAccountId;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setTransferId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

