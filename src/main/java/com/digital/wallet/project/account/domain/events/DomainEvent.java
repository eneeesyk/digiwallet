package com.digital.wallet.project.account.domain.events;

import java.time.Instant;

import com.digital.wallet.project.account.objects.AccountId;


public abstract class DomainEvent {
    private final AccountId accountId;
    private final String eventId;
    private final Instant occurredAt;

    public DomainEvent(AccountId accountId, String eventId) {
        this.accountId = accountId;
        this.eventId = eventId;
        this.occurredAt = Instant.now();
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }
    public AccountId getAccountId() {
        return accountId;
    }
}
