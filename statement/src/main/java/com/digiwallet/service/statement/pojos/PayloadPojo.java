package com.digiwallet.service.statement.pojos;

public class PayloadPojo {
    
    private AccountIdPojo accountId;
    private MoneyPojo amount;
    private MoneyPojo initialBalance;

    public AccountIdPojo getAccountId() {
        return accountId;
    }

    public MoneyPojo getAmount() {
        return amount;
    }

    public MoneyPojo getInitialBalance(){
        return initialBalance;
    }
}
