package com.atm.service;

import com.atm.dao.AccountDAO;
import com.atm.dao.impl.AccountMyBatisDAO;
import com.atm.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class AccountService {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(AccountService.class);
    private final AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountMyBatisDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account authenticate(int accountNumber, String pin) {
        Account acc = accountDAO.findByAccountNumber(accountNumber);
        if (acc != null && acc.getPin().equals(pin)) {
            LOGGER.info("Authentication successful for account " + accountNumber);
            return acc;
        }
        LOGGER.warn("Authentication failed for account " + accountNumber);
        return null;
    }

    public void updateBalance(Account acc, double newBalance) {
        acc.setBalance(newBalance);
        accountDAO.updateBalance(acc);
        LOGGER.info("Balance updated for account " + acc.getAccountNumber());
    }
}

