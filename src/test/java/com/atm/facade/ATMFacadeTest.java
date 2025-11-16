package com.atm.facade;

import com.atm.model.Account;
import com.atm.dao.AccountDAO;
import com.atm.factory.DAOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMFacadeTest {

    private ATMFacade atmFacade;
    private AccountDAO accountDAO;

    @BeforeEach
    void setUp() {

        accountDAO = DAOFactory.createAccountDAO();

        Account acc = new Account("12345", "1111", 500.0);

        accountDAO.updateBalance(acc);

        atmFacade = new ATMFacade();
    }

    @Test
    void testAuthenticateSuccess() {
        Account acc = atmFacade.authenticate("12345", "1111");
        assertNotNull(acc, "Authentication should succeed for valid credentials");
        assertEquals("12345", acc.getAccountNumber());
    }

    @Test
    void testAuthenticateFail() {
        Account acc = atmFacade.authenticate("12345", "9999");
        assertNull(acc, "Authentication should fail for wrong PIN");
    }

    @Test
    void testDeposit() {
        Account acc = atmFacade.authenticate("12345", "1111");
        assertNotNull(acc);

        atmFacade.deposit(acc, 200.0);

        assertEquals(700.0, acc.getBalance(), 0.01,
                "Deposit should increase account balance");
    }

    @Test
    void testWithdrawSuccess() {
        Account acc = atmFacade.authenticate("12345", "1111");
        assertNotNull(acc);

        String result = atmFacade.withdraw(acc, 100);

        assertTrue(result.toUpperCase().contains("SUCCESS"),
                "Withdrawal should return SUCCESS");

        assertEquals(400.0, acc.getBalance(), 0.01,
                "Balance should decrease after successful withdrawal");
    }

    @Test
    void testWithdrawInsufficientFunds() {
        Account acc = atmFacade.authenticate("12345", "1111");
        assertNotNull(acc);

        String result = atmFacade.withdraw(acc, 1000);

        assertTrue(
                result.toUpperCase().contains("FAIL") ||
                        result.toUpperCase().contains("INSUFFICIENT"),
                "Withdrawal should fail for insufficient funds"
        );

        assertEquals(500.0, acc.getBalance(), 0.01);
    }
}
