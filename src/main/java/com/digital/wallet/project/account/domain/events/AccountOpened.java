package com.digital.wallet.project.account.domain.events;

import com.digital.wallet.project.account.domain.Money;
import java.util.UUID;

public class AccountOpened extends DomainEvent{
    private final Money initialBalance;

    public AccountOpened(long accountId, Money initialBalance) {
        super(accountId, UUID.randomUUID().toString());
        this.initialBalance = initialBalance;
    }

    public Money getInitialBalance() {
        return initialBalance;
    }
}
