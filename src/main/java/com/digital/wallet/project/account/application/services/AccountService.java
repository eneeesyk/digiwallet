package com.digital.wallet.project.account.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digital.wallet.project.account.domain.Account;
import com.digital.wallet.project.account.domain.events.DomainEvent;
import com.digital.wallet.project.account.infrastructure.repositories.AccountBalanceRepository;
import com.digital.wallet.project.account.infrastructure.repositories.EventRepository;
import com.digital.wallet.project.account.infrastructure.serializers.EventSerializer;
import com.digital.wallet.project.account.infrastructure.entities.AccountBalanceEntity;
import com.digital.wallet.project.account.infrastructure.entities.EventEntity;
import com.digital.wallet.project.account.domain.Money;
import com.digital.wallet.project.account.objects.AccountId;
import com.digital.wallet.project.config.RabbitMqFanoutExchangeConfig;
import com.digital.wallet.project.account.infrastructure.messaging.EventPublisher;

@Service
public class AccountService {

    private EventRepository eventRepository;
    private EventSerializer eventSerializer;
    private AccountBalanceRepository accountBalanceRepository;
    private EventPublisher eventPublisher;

    public AccountService(EventRepository eventRepository, EventSerializer eventSerializer, AccountBalanceRepository accountBalanceRepository, EventPublisher eventPublisher) {
        this.eventRepository = eventRepository;
        this.eventSerializer = eventSerializer;
        this.accountBalanceRepository = accountBalanceRepository;
        this.eventPublisher = eventPublisher;
    }

    public void registerEvents(Account account){
        List<DomainEvent> events = account.getUncommittedEvents();
        for (DomainEvent event: events){
            EventEntity eventEntity = new EventEntity();
            eventEntity.setAccountId(event.getAccountId().getValue());
            eventEntity.setEventType(event.getClass().getSimpleName());
            eventEntity.setEventData(eventSerializer.serialize(event));
            eventEntity.setOccurredAt(event.getOccurredAt());
            eventRepository.save(eventEntity);

            accountBalanceRepository.save(new AccountBalanceEntity(
                account.getAccountId().getValue(),
                account.getBalance().getAmount(),
                account.getBalance().getCurrency(),
                event.getOccurredAt()
            ));

            eventPublisher.publishMessage(RabbitMqFanoutExchangeConfig.FANOUT_EXCHANGE, event, "");
        }
        account.clearUncommittedEvents();
    }
    

    public Account loadAccount(AccountId accountId){
        List<EventEntity> events = eventRepository.findByAccountIdOrderByOccurredAtAsc(accountId.getValue());
        List<DomainEvent> domainEvents = new ArrayList<>();
        for (EventEntity event: events) {
            domainEvents.add(eventSerializer.deserialize(event.getEventData(), event.getEventType()));
        }

        return Account.reconstructFromEvents(accountId, domainEvents);
    }

    public void openAccount(AccountId accountId, Money initialBalance){
        Account account = Account.openAccount(accountId, initialBalance);
        registerEvents(account);
    }

    public void deposit(AccountId accountId, Money amount){
        Account account = loadAccount(accountId);
        account.deposit(amount);
        registerEvents(account);
    }

    public void withdraw(AccountId accountId, Money amount){
        Account account = loadAccount(accountId);
        account.withdraw(amount);
        registerEvents(account);
    }

    public Money getBalance(AccountId accountId){
        AccountBalanceEntity accountBalance = accountBalanceRepository.findByAccountId(accountId.getValue());
        if (accountBalance == null) {
            throw new IllegalArgumentException("Account not found");
        }
        return new Money(accountBalance.getBalance(), accountBalance.getCurrency()); 
    }
}
