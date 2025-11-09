package com.atm.service;

import com.atm.model.Account;

import java.util.Map;

public class ATMService {

    private final AccountService accountService;
    private final CashDispenser cashDispenser;

    public ATMService() {
        this.accountService = new AccountService();
        this.cashDispenser = new CashDispenser();
    }

    public void viewBalance(Account acc) {
        System.out.println("Your current balance is: $" + acc.getBalance());
    }

    public void deposit(Account acc, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        acc.setBalance(acc.getBalance() + amount);
        accountService.updateBalance(acc, acc.getBalance());
        System.out.println("Successfully deposited $" + amount);
    }

    public void withdraw(Account acc, int amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (acc.getBalance() < amount) {
            System.out.println("Insufficient balance.");
            return;
        }

        try {
            Map<Integer, Integer> notes = cashDispenser.dispense(amount);
            acc.setBalance(acc.getBalance() - amount);
            accountService.updateBalance(acc, acc.getBalance());
            System.out.println("Please collect your cash:");
            notes.forEach((denom, count) ->
                    System.out.println(count + " x $" + denom)
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
