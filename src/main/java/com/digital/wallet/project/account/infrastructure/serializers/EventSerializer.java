package com.digital.wallet.project.account.infrastructure.serializers;

import com.digital.wallet.project.account.domain.events.DomainEvent;
import com.digital.wallet.project.constants.DWConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.digital.wallet.project.account.domain.events.AccountOpened;
import com.digital.wallet.project.account.domain.events.MoneyDeposited;
import com.digital.wallet.project.account.domain.events.MoneyWithdrawn;

public class EventSerializer {
    

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String serialize(DomainEvent event) {

        try {
            return objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            throw new RuntimeException("Could not serialize event: " + event.getEventId(), e);
        }
    }

    public DomainEvent deserialize(String eventData, String eventType){
        try {
            if (DWConstants.ACCOUNT_OPENED.equals(eventType)) {
                return objectMapper.readValue(eventData, AccountOpened.class);
            } else if (DWConstants.MONEY_DEPOSITED.equals(eventType)) {
                return objectMapper.readValue(eventData, MoneyDeposited.class);
            } else if (DWConstants.MONEY_WITHDRAWN.equals(eventType)) {
                return objectMapper.readValue(eventData, MoneyWithdrawn.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not deserialize event: " + eventType, e);
        }
        throw new IllegalArgumentException("Unknown event type: " + eventType);
    }
}
