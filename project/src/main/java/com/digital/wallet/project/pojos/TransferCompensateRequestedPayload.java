package com.digital.wallet.project.pojos;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferCompensateRequestedPayload {

    private UUID transferId;

    private Long accountId;

    private BigDecimal amount;

    private String currency;

    public TransferCompensateRequestedPayload(UUID transferId, Long accountId, BigDecimal amount, String currency) {
        this.transferId = transferId;
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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


    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }
}
