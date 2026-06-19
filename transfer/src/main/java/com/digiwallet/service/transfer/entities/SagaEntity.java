package com.digiwallet.service.transfer.entities;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.digiwallet.service.transfer.StatusEnum;;

@Entity
@Table(name = "saga")
public class SagaEntity {

    @Id
    private UUID transferId;

    @Column(name = "from_account_id", nullable = false)
    private Long fromAccountId;

    @Column(name = "to_account_id", nullable = false)
    private Long toAccountId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    public SagaEntity() {
    }

    public SagaEntity(UUID transferId, Long fromAccountId, Long toAccountId, BigDecimal amount, String currency, StatusEnum status){
        this.fromAccountId = fromAccountId;
        this.amount = amount;
        this.toAccountId = toAccountId;
        this.transferId = transferId;
        this.currency = currency;
        this.status = status;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

}
