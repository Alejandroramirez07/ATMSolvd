package com.atm.dao;

import com.atm.model.Account;

public interface AccountDAO {

    /**
     * @param accountNumber the account number to search for
     * @return Account object or null if not found
     */
    Account findByAccountNumber(int accountNumber);

    /**
     * @param account the account to update
     */
    void updateBalance(Account account);
}
