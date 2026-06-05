package com.digital.wallet.project.account.domain.events;
import com.digital.wallet.project.account.domain.Money;
import java.util.UUID;

public class MoneyWithdrawn extends DomainEvent{
    private final Money amount;

    public MoneyWithdrawn(long accountId, Money amount){
        super(accountId, UUID.randomUUID().toString());
        this.amount = amount;
    }

    public Money getAmount() {
        return amount;
    }
    
}
