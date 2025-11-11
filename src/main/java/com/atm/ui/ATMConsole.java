package com.atm.ui;

import com.atm.app.Main;
import com.atm.model.Account;
import com.atm.service.ATMService;
import com.atm.service.CashDispenser;
import com.atm.strategy.GreedyDispenseStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.Scanner;

public class ATMConsole {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(ATMConsole.class);

    private final ATMService atmService;
    private final Scanner scanner;
    private Account currentAccount;

    public ATMConsole() {
        this.scanner = new Scanner(System.in);
        this.atmService = new ATMService(new CashDispenser(new GreedyDispenseStrategy()));
    }

    public void start() {
        LOGGER.info("=== Welcome to the ATM ===");
        System.out.print("Insert card (enter account number): ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        currentAccount = atmService.authenticate(accountNumber, pin);
        if (currentAccount == null) {
            LOGGER.info("Invalid credentials. Exiting...");
            return;
        }

        showMenu();
    }

    private void showMenu() {
        while (true) {
            LOGGER.info("\n=== ATM Menu ===");
            LOGGER.info("1. View Balance");
            LOGGER.info("2. Deposit Cash");
            LOGGER.info("3. Withdraw Cash");
            LOGGER.info("4. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    atmService.viewBalance(currentAccount);
                    break;
                case "2":
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = Double.parseDouble(scanner.nextLine());
                    atmService.deposit(currentAccount, depositAmount);
                    break;
                case "3":
                    System.out.print("Enter withdrawal amount: ");
                    int withdrawAmount = Integer.parseInt(scanner.nextLine());
                    atmService.withdraw(currentAccount, withdrawAmount);
                    break;
                case "4":
                    LOGGER.info("Thank you for using our ATM!");
                    return;
                default:
                    LOGGER.info("Invalid option. Try again.");
            }
        }
    }
}
