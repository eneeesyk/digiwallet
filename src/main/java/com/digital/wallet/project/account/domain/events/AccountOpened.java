package com.digital.wallet.project.account.domain.events;

import com.digital.wallet.project.account.domain.Money;
import com.digital.wallet.project.account.objects.AccountId;
import com.digital.wallet.project.constants.EventType;

import java.util.UUID;

public class AccountOpened extends DomainEvent{
    private final Money initialBalance;

    public AccountOpened(AccountId accountId, Money initialBalance) {
        super(accountId, UUID.randomUUID().toString());
        this.initialBalance = initialBalance;
    }

    public Money getInitialBalance() {
        return initialBalance;
    }

    public EventType getType(){
        return EventType.ACCOUNT_OPENED;
    }
}
