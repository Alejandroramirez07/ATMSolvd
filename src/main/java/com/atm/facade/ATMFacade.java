package com.atm.facade;

import com.atm.model.Account;
import com.atm.service.ATMService;
import com.atm.service.CashDispenser;
import com.atm.strategy.GreedyDispenseStrategy;
import com.atm.dao.AccountDAO;
import com.atm.factory.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.Scanner;

public class ATMFacade {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(ATMFacade.class);
    private final ATMService atmService;
    private final Scanner scanner;

    public ATMFacade() {
        AccountDAO accountDAO = DAOFactory.createAccountDAO();
        CashDispenser dispenser = new CashDispenser(new GreedyDispenseStrategy());
        this.atmService = new ATMService(accountDAO, dispenser);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        LOGGER.info("=== Welcome to the ATM ===");
        System.out.print("Insert card (enter account number): ");
        String accNum = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        Account acc = atmService.authenticate(accNum, pin);
        if (acc == null) {
            LOGGER.info("Invalid credentials. Exiting...");
            return;
        }

        showMenu(acc);
    }

    private void showMenu(Account acc) {
        while (true) {
            LOGGER.info("\n=== ATM Menu ===");
            LOGGER.info("1. View Balance");
            LOGGER.info("2. Deposit Cash");
            LOGGER.info("3. Withdraw Cash");
            LOGGER.info("4. Exit");
            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> atmService.viewBalance(acc);
                case 2 -> handleDeposit(acc);
                case 3 -> handleWithdraw(acc);
                case 4 -> {
                    LOGGER.info("Thank you for using our ATM. Goodbye!");
                    return;
                }
                default -> LOGGER.info("Invalid option. Try again.");
            }
        }
    }

    private void handleDeposit(Account acc) {
        System.out.print("Enter deposit amount: ");
        double amount = readDouble();
        atmService.deposit(acc, amount);
    }

    private void handleWithdraw(Account acc) {
        System.out.print("Enter withdrawal amount: ");
        int amount = readInt();
        atmService.withdraw(acc, amount);
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private double readDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
