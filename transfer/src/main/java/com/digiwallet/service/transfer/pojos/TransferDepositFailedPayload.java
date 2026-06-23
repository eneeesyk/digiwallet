package com.digiwallet.service.transfer.pojos;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferDepositFailedPayload {
    
    private UUID transferId;

    private Long fromAccountId;

    private BigDecimal amount;

    private String currency;

    private String reason;

    public TransferDepositFailedPayload(UUID transferId, Long fromAccountId, BigDecimal amount, String currency ,String reason) {
        this.transferId = transferId;
        this.reason = reason;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.currency = currency;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
