package com.digital.wallet.project.account.domain.events;

import java.time.Instant;


public abstract class DomainEvent {
    private final long accountId;
    private final String eventId;
    private final Instant occuredAt;

    public DomainEvent(long accountId, String eventId) {
        this.accountId = accountId;
        this.eventId = eventId;
        this.occuredAt = Instant.now();
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getOccuredAt() {
        return occuredAt;
    }
    public long getAccountId() {
        return accountId;
    }
}
