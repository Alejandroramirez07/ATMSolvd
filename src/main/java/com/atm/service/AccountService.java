package com.atm.service;

import com.atm.dao.impl.AccountMyBatisDAO;
import com.atm.model.Account;

public class AccountService {
    private final AccountMyBatisDAO accountDAO = new AccountMyBatisDAO();

    public Account authenticate(int accountNumber, String pin) {
        Account acc = accountDAO.findByAccountNumber(accountNumber);
        if (acc != null && acc.getPin().equals(pin)) {
            return acc;
        }
        return null;
    }

    public void updateBalance(Account acc, double newBalance) {
        acc.setBalance(newBalance);
        accountDAO.updateBalance(acc);
    }
}
