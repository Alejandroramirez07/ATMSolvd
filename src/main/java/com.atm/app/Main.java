package com.atm.app;

import com.atm.model.Account;
import com.atm.service.ATMService;
import com.atm.service.AccountService;
import com.atm.service.CashDispenser;
import com.atm.strategy.GreedyDispenseStrategy;

import java.util.Scanner;

/**
 * Main class to run the ATM application.
 * This class now contains the console application logic.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Account currentAccount;

    public static void main(String[] args) {

        CashDispenser cashDispenser = new CashDispenser(new GreedyDispenseStrategy());

        ATMService atmService = new ATMService(cashDispenser);

        System.out.println("================================");
        System.out.println("  WELCOME TO THE JAVA ATM SIM   ");
        System.out.println("================================");
        System.out.println("Test Accounts: 123456/1234 ($1500) | 987654/9999 ($500)");

        if (!authenticateUser(atmService)) {
            return;
        }

        showMenu(atmService);
    }

    private static boolean authenticateUser(ATMService atmService) {
        System.out.print("\nInsert card (enter account number): ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        currentAccount = atmService.authenticate(accountNumber, pin);
        if (currentAccount == null) {
            System.out.println("Invalid credentials. Please try again.");
            return false;
        }
        System.out.println("\nWelcome, Account " + currentAccount.getAccountNumber() + "!");
        return true;
    }

    private static void showMenu(ATMService atmService) {
        while (true) {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Cash (Whole $ only)");
            System.out.println("3. Withdraw Cash");
            System.out.println("4. Exit/Eject Card");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            try {
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
                        System.out.print("Enter withdrawal amount (Multiples of $10 only): ");
                        int withdrawAmount = Integer.parseInt(scanner.nextLine());
                        atmService.withdraw(currentAccount, withdrawAmount);
                        break;
                    case "4":
                        System.out.println("Thank you for using our ATM! Card ejected.");
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input Error: Please enter a valid number for the amount/option.");
            }
        }
    }
}