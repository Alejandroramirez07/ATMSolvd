package com.atm.facade;

import com.atm.dao.AccountDAO;
import com.atm.factory.DAOFactory;
import com.atm.model.Account;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ATMFacadeTest {

    private ATMFacade atmFacade;
    private AccountDAO accountDAO;

    private static final String TEST_ACC_NUMBER = "111111";
    private static final String TEST_PIN = "1111";

    @BeforeEach
    void setUp() {
        atmFacade = new ATMFacade();
        accountDAO = DAOFactory.createAccountDAO();
    }

    @Test
    void testAuthenticateSuccess() {
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);
        assertNotNull(acc, "Authentication should succeed for valid credentials");
    }

    @Test
    void testDeposit() {
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);
        assertNotNull(acc);

        double before = acc.getBalance();
        atmFacade.deposit(acc, 100.0);

        Account updated = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
        assertEquals(before + 100.0, updated.getBalance(), 0.01);
    }

    @Test
    void testWithdrawInsufficientFunds() {
        Account acc = atmFacade.authenticate(TEST_ACC_NUMBER, TEST_PIN);
        assertNotNull(acc);

        double before = acc.getBalance();
        int tooMuch = (int) (before + 5000);

        String result = atmFacade.withdraw(acc, tooMuch);

        assertTrue(result.toLowerCase().contains("insufficient")
                        || result.toLowerCase().contains("fail"),
                "Expected insufficient funds message but got: " + result);

        Account updated = accountDAO.findByAccountNumber(Integer.parseInt(TEST_ACC_NUMBER));
        assertEquals(before, updated.getBalance(), 0.01);
    }
}
