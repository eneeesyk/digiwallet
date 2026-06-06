package com.digital.wallet.project.account.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digital.wallet.project.account.domain.Account;
import com.digital.wallet.project.account.domain.events.DomainEvent;
import com.digital.wallet.project.account.infrastructure.repositories.EventRepository;
import com.digital.wallet.project.account.infrastructure.serializers.EventSerializer;
import com.digital.wallet.project.account.infrastructure.entities.EventEntity;
import com.digital.wallet.project.account.domain.Money;

@Service
public class AccountService {

    private EventRepository eventRepository;
    private EventSerializer eventSerializer;

    public AccountService(EventRepository eventRepository, EventSerializer eventSerializer) {
        this.eventRepository = eventRepository;
        this.eventSerializer = eventSerializer;
    }

    public void registerEvents(Account account){
        List<DomainEvent> events = account.getUncommittedEvents();
        for (DomainEvent event: events){
            EventEntity eventEntity = new EventEntity();
            eventEntity.setAccountId(event.getAccountId());
            eventEntity.setEventType(event.getClass().getSimpleName());
            eventEntity.setEventData(eventSerializer.serialize(event));
            eventEntity.setOccurredAt(event.getOccuredAt());
            eventRepository.save(eventEntity);
        }
        account.clearUncommittedEvents();
    }
    

    public Account replayEvents(Long accountId){
        List<EventEntity> events = eventRepository.findByAccountIdOrderByOccurredAtAsc(accountId);
        List<DomainEvent> domainEvents = new ArrayList<>();
        for (EventEntity event: events) {
            domainEvents.add(eventSerializer.deserialize(event.getEventData(), event.getEventType()));
        }

        return Account.reconstructFromEvents(accountId, domainEvents);
    }

    public void openAccount(Long accountId, Money initialBalance){
        Account account = Account.openAccount(accountId, initialBalance);
        registerEvents(account);
    }

    public void deposit(Long accountId, Money amount){
        Account account = replayEvents(accountId);
        account.deposit(amount);
        registerEvents(account);
    }

    public void withdraw(Long accountId, Money amount){
        Account account = replayEvents(accountId);
        account.withdraw(amount);
        registerEvents(account);
    }
}
