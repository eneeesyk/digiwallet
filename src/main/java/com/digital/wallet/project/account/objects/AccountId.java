package com.digital.wallet.project.account.objects;

public class AccountId {

    private final long value;

    public AccountId(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return value == accountId.value;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(value);
    }
    
}
