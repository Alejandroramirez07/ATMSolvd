package com.atm.service;

import com.atm.model.Account;
import com.atm.repository.AccountRepository;

public class AuthService {
    private final AccountRepository repository;

    public AuthService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account authenticate(String accountNumber, String pin) {
        Account acc = repository.findByAccountNumber(accountNumber);
        if (acc != null && acc.getPin().equals(pin)) {
            return acc;
        }
        return null;
    }
}
