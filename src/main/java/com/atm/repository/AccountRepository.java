package com.atm.repository;

import com.atm.model.Account;

public interface AccountRepository {
    Account findByAccountNumber(String accountNumber);
    void update(Account account);
}
