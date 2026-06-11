package com.digital.wallet.project.account.domain;

import java.util.ArrayList;
import java.util.List;

import com.digital.wallet.project.account.domain.events.AccountOpened;
import com.digital.wallet.project.account.domain.events.DomainEvent;
import com.digital.wallet.project.account.domain.events.MoneyDeposited;
import com.digital.wallet.project.account.domain.events.MoneyWithdrawn;
import com.digital.wallet.project.account.objects.AccountId;

public class Account {
    private final AccountId accountId;
    private Money balance;

    private List<DomainEvent> uncommittedEvents = new ArrayList<>();

    private Account(AccountId accountId){
        this.accountId = accountId;
    }

    public static Account openAccount(AccountId accountId, Money initialBalance){
        Account account = new Account(accountId);
        AccountOpened event = new AccountOpened(accountId, initialBalance);
        account.uncommittedEvents.add(event);
        account.apply(event);
        return account;

    }

    public static Account reconstructFromEvents(AccountId accountId, List<DomainEvent> events){
        Account account = new Account(accountId);
        for (DomainEvent event : events) {
            account.apply(event);
        }
        return account;
    }

    public void deposit(Money amount){
        if(amount.isNegative()){
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        MoneyDeposited event = new MoneyDeposited(accountId, amount);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void withdraw(Money amount){
        if(amount.isNegative()){
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }
        if(balance.getAmount().compareTo(amount.getAmount()) < 0){
            throw new IllegalArgumentException("Can not withdraw that amount money because of balance");
        }
        MoneyWithdrawn event = new MoneyWithdrawn(accountId, amount);
        uncommittedEvents.add(event);
        apply(event);
    }

    private void apply(DomainEvent event){
        if (event instanceof AccountOpened e){
            this.balance = e.getInitialBalance();
        } else if (event instanceof MoneyDeposited e){
            this.balance = this.balance.add(e.getAmount());
        } else if (event instanceof MoneyWithdrawn e){
            this.balance = this.balance.subtract(e.getAmount());
        }
    }

    public List<DomainEvent> getUncommittedEvents() {
        return uncommittedEvents;
    }

    public void clearUncommittedEvents() {
        uncommittedEvents.clear();
    }

    public Money getBalance() {
        return balance;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}
