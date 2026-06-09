package com.digital.wallet.project.account.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

import com.digital.wallet.project.account.objects.AccountId;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "event_data", nullable = false)
    private String eventData;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    public EventEntity() {
    }

    public Long getId() {
        return id;
    }

    public AccountId getAccountId() {
        return new AccountId(accountId);
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventData() {
        return eventData;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }
}
