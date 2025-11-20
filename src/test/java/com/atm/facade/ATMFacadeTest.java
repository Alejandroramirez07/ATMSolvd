package com.atm.facade;

import com.atm.dao.AccountDAO;
import com.atm.factory.DAOFactory;
import com.atm.model.Account;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ATMFacadeTest {

    private ATMFacade atmFacade;
    private AccountDAO accountDAO;

    private static final String TEST_ACC_NUMBER = "123456";
    private static final String TEST_PIN = "1234";
    private static final String INVALID_ACC_NUMBER = "999999";
    private static final String INVALID_PIN = "9999";

    @BeforeEach
    void setUp() {
        atmFacade = new ATMFacade();
        accountDAO = DAOFactory.createAccountDAO();

        // Ensure test account exists and has known balance
        Account testAccount = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
        if (testAccount == null) {
            fail("Test account " + TEST_ACC_NUMBER + " does not exist in database");
        }
    }

    @Test
    void testAuthenticateSuccess() {
        // When
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);

        // Then
        assertNotNull(acc, "Authentication should succeed for valid credentials");
        assertEquals(TEST_ACC_NUMBER, String.valueOf(acc.getAccountNumber()));
    }

    @Test
    void testAuthenticateFailure() {
        // When
        Account acc = atmFacade.authenticate(INVALID_ACC_NUMBER, INVALID_PIN);

        // Then
        assertNull(acc, "Authentication should fail for invalid credentials");
    }

    @Test
    void testDeposit() {
        // Given
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);
        assertNotNull(acc, "Account should be authenticated first");

        double initialBalance = acc.getBalance();
        double depositAmount = 100.0;

        // When
        atmFacade.deposit(acc, depositAmount);

        // Then - verify balance updated
        Account updatedAccount = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
        assertNotNull(updatedAccount, "Account should exist after deposit");
        assertEquals(initialBalance + depositAmount, updatedAccount.getBalance(), 0.01,
                "Balance should increase by deposit amount");
    }

    @Test
    void testWithdrawInsufficientFunds() {
        // Given
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);
        assertNotNull(acc, "Account should be authenticated first");

        double initialBalance = acc.getBalance();
        int withdrawalAmount = (int) (initialBalance + 5000); // More than available

        // When
        String result = atmFacade.withdraw(acc, withdrawalAmount);

        // Then
        assertNotNull(result, "Withdrawal should return a message");
        assertTrue(result.toLowerCase().contains("insufficient") ||
                        result.toLowerCase().contains("fail") ||
                        result.toLowerCase().contains("error"),
                "Expected insufficient funds message but got: " + result);

        // Verify balance unchanged
        Account updatedAccount = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
        assertEquals(initialBalance, updatedAccount.getBalance(), 0.01,
                "Balance should not change after failed withdrawal");
    }

    @Test
    void testWithdrawSuccess() {
        // Given
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);
        assertNotNull(acc, "Account should be authenticated first");

        double initialBalance = acc.getBalance();
        int withdrawalAmount = 100; // Reasonable amount

        // When - only proceed if account has sufficient funds
        if (initialBalance >= withdrawalAmount) {
            String result = atmFacade.withdraw(acc, withdrawalAmount);

            // Then
            assertNotNull(result, "Withdrawal should return a message");

            Account updatedAccount = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
            assertEquals(initialBalance - withdrawalAmount, updatedAccount.getBalance(), 0.01,
                    "Balance should decrease by withdrawal amount");
        } else {
            System.out.println("Skipping successful withdrawal test - insufficient funds in test account");
        }
    }
}