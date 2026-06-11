package com.digital.wallet.project.account.domain.events;

import com.digital.wallet.project.account.domain.Money;
import com.digital.wallet.project.account.objects.AccountId;
import com.digital.wallet.project.constants.EventType;

import java.util.UUID;

public class MoneyWithdrawn extends DomainEvent{
    private final Money amount;

    public MoneyWithdrawn(AccountId accountId, Money amount){
        super(accountId, UUID.randomUUID().toString());
        this.amount = amount;
    }

    public Money getAmount() {
        return amount;
    }
    
    public EventType getType(){
        return EventType.MONEY_WITHDRAWN;
    }
}
