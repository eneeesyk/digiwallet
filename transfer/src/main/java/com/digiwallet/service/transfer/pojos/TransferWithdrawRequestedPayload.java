package com.digiwallet.service.transfer.pojos;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferWithdrawRequestedPayload {
    
    private UUID transferId;

    private Long fromAccountId;

    private BigDecimal amount;

    private String currency;

    public TransferWithdrawRequestedPayload() {
    }

    public TransferWithdrawRequestedPayload(UUID transferId, Long fromAccountId, BigDecimal amount, String currency) {
        this.transferId = transferId;
        this.fromAccountId = fromAccountId;
        this.amount = amount;
        this.currency = currency;
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
