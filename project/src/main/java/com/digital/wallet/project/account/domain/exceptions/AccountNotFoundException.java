package com.digital.wallet.project.account.domain.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
