package com.atm.app;

import com.atm.facade.ATMFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Main {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("================================");
        LOGGER.info("  WELCOME TO THE JAVA ATM SIM   ");
        LOGGER.info("================================");
        LOGGER.info("Test Accounts: 123456/1234 ($1500) | 987654/9999 ($500)");
        LOGGER.info("================================");

        // 🧩 Only one line needed to start the entire app
        ATMFacade atm = new ATMFacade();
        atm.start();
    }
}
