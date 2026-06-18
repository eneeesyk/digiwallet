package com.digiwallet.service.statement.pojos;

import java.math.BigDecimal;

public class MoneyPojo {
    
    private final BigDecimal amount;
    private final String currency;
    
    public MoneyPojo(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
