package com.atm.service;

import com.atm.app.Main;
import com.atm.dao.AccountDAO;
import com.atm.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.Map;

public class ATMService {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(ATMService.class);

    private final AccountService accountService;
    private final CashDispenser cashDispenser;

    public ATMService(CashDispenser cashDispenser) {
        this.accountService = new AccountService();
        this.cashDispenser = cashDispenser;
    }

    public ATMService(AccountDAO accountDAO, CashDispenser cashDispenser) {
        this.accountService = new AccountService(accountDAO);
        this.cashDispenser = cashDispenser;
    }

    public Account authenticate(String accountNumber, String pin) {
        return accountService.authenticate(Integer.parseInt(accountNumber), pin);
    }

    public void viewBalance(Account acc) {
        LOGGER.info("Your current balance is: $" + acc.getBalance());
    }

    public void deposit(Account acc, double amount) {
        if (amount <= 0) {
            LOGGER.info("Invalid deposit amount.");
            return;
        }
        acc.setBalance(acc.getBalance() + amount);
        accountService.updateBalance(acc, acc.getBalance());
        LOGGER.info("Successfully deposited $" + amount);
    }

    public String withdraw(Account acc, int amount) {
        if (amount <= 0) {
            return "Invalid withdrawal amount.";

        }
        if (acc.getBalance() < amount) {
            return "Insufficient balance.";
        }
        try {
            Map<Integer, Integer> notes = cashDispenser.dispense(amount);
            acc.setBalance(acc.getBalance() - amount);
            accountService.updateBalance(acc, acc.getBalance());

            StringBuilder sb = new StringBuilder("Please collect your cash:");
            notes.forEach((denom, count) ->
                    sb.append(count).append(" x $").append(denom).append("\n")
            );

            return sb.toString();

        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }
}
