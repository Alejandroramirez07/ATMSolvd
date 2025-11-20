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

        Account testAccount = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
        if (testAccount == null) {
            fail("Test account " + TEST_ACC_NUMBER + " does not exist in database");
        }
    }

    @Test
    void testAuthenticateSuccess() {
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);

        assertNotNull(acc, "Authentication should succeed for valid credentials");
        assertEquals(TEST_ACC_NUMBER, String.valueOf(acc.getAccountNumber()));
    }

    @Test
    void testAuthenticateFailure() {

        Account acc = atmFacade.authenticate(INVALID_ACC_NUMBER, INVALID_PIN);

        assertNull(acc, "Authentication should fail for invalid credentials");
    }

    @Test
    void testDeposit() {
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);
        assertNotNull(acc, "Account should be authenticated first");

        double initialBalance = acc.getBalance();
        double depositAmount = 100.0;

        atmFacade.deposit(acc, depositAmount);

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
        int withdrawalAmount = (int) (initialBalance + 5000);

        String result = atmFacade.withdraw(acc, withdrawalAmount);

        assertNotNull(result, "Withdrawal should return a message");
        assertTrue(result.toLowerCase().contains("insufficient") ||
                        result.toLowerCase().contains("fail") ||
                        result.toLowerCase().contains("error"),
                "Expected insufficient funds message but got: " + result);

        Account updatedAccount = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
        assertEquals(initialBalance, updatedAccount.getBalance(), 0.01,
                "Balance should not change after failed withdrawal");
    }

    @Test
    void testWithdrawSuccess() {
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);
        assertNotNull(acc, "Account should be authenticated first");

        double initialBalance = acc.getBalance();
        int withdrawalAmount = 100;

        if (initialBalance >= withdrawalAmount) {
            String result = atmFacade.withdraw(acc, withdrawalAmount);

            assertNotNull(result, "Withdrawal should return a message");

            Account updatedAccount = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
            assertEquals(initialBalance - withdrawalAmount, updatedAccount.getBalance(), 0.01,
                    "Balance should decrease by withdrawal amount");
        } else {
            System.out.println("Skipping successful withdrawal test - insufficient funds in test account");
        }
    }
}