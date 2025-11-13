package com.atm.service;

import com.atm.model.Account;
import com.atm.strategy.GreedyDispenseStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMServiceTest {

    private ATMService atmService;
    private Account testAccount;

    @BeforeEach
    void setup() {
        atmService = new ATMService(new CashDispenser(new GreedyDispenseStrategy()));
        testAccount = new Account( "123456", "1234", 1000);
    }

    @Test
    void testDeposit() {
        atmService.deposit(testAccount, 500);
        assertEquals(1500, testAccount.getBalance());
    }

    @Test
    void testWithdraw() {
        atmService.withdraw(testAccount, 300);
        assertEquals(700, testAccount.getBalance());
    }

    @Test
    void testWithdrawTooMuch() {
        String result = atmService.withdraw(testAccount, 2000);
        assertTrue(result.contains("Insufficient"), "Should warn about insufficient balance");
    }
}
