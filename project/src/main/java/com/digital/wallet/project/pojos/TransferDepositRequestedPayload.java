package com.digital.wallet.project.pojos;

import java.math.BigDecimal;
import java.util.UUID;


public class TransferDepositRequestedPayload {
    
    private UUID transferId;

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal amount;

    private String currency;

    public TransferDepositRequestedPayload() {
    }

    public TransferDepositRequestedPayload(UUID transferId, Long fromAccountId, BigDecimal amount, String currency, Long toAccountId) {
        this.transferId = transferId;
        this.fromAccountId = fromAccountId;
        this.amount = amount;
        this.currency = currency;
        this.toAccountId = toAccountId;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
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
