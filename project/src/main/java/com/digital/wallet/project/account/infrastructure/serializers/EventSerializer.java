package com.digital.wallet.project.account.infrastructure.serializers;

import com.digital.wallet.project.account.domain.events.DomainEvent;
import com.digital.wallet.project.constants.EventType;

import org.springframework.stereotype.Component;

import com.digital.wallet.project.account.domain.events.AccountOpened;
import com.digital.wallet.project.account.domain.events.MoneyDeposited;
import com.digital.wallet.project.account.domain.events.MoneyWithdrawn;

import com.google.gson.Gson;

@Component
public class EventSerializer {
    
    Gson gson = new Gson();

    public String serialize(DomainEvent event) {

        try {
            return gson.toJson(event);
        } catch (Exception e) {
            throw new RuntimeException("Could not serialize event: " + event.getEventId(), e);
        }
    }

    public DomainEvent deserialize(String eventData, EventType eventType){
        try {
            if (EventType.ACCOUNT_OPENED.equals(eventType)) {
                return gson.fromJson(eventData, AccountOpened.class);
            } else if (EventType.MONEY_DEPOSITED.equals(eventType)) {
                return gson.fromJson(eventData, MoneyDeposited.class);
            } else if (EventType.MONEY_WITHDRAWN.equals(eventType)) {
                return gson.fromJson(eventData, MoneyWithdrawn.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not deserialize event: " + eventType, e);
        }
        throw new IllegalArgumentException("Unknown event type: " + eventType);
    }
}
