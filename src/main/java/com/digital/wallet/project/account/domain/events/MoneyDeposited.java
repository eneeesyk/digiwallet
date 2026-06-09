package com.digital.wallet.project.account.domain.events;

import com.digital.wallet.project.account.domain.Money;
import com.digital.wallet.project.account.objects.AccountId;

import java.util.UUID;

public class MoneyDeposited extends DomainEvent{
    private final Money amount;

    public MoneyDeposited(AccountId accountId, Money amount){
        super(accountId, UUID.randomUUID().toString());
        this.amount = amount;
    }

    public Money getAmount() {
        return amount;
    }
}
