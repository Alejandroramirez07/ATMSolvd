package com.atm.repository;

import com.atm.model.Account;
import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public InMemoryAccountRepository() {
        accounts.put("1111", new Account("1111", "1234", 2500));
        accounts.put("2222", new Account("2222", "4321", 4000));
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public void update(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }
}
