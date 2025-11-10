package com.atm.ui;

import com.atm.model.Account;
import com.atm.service.ATMService;
import com.atm.service.CashDispenser;
import com.atm.strategy.GreedyDispenseStrategy;

import java.util.Scanner;

public class ATMConsole {

    private final ATMService atmService;
    private final Scanner scanner;
    private Account currentAccount;

    public ATMConsole() {
        this.scanner = new Scanner(System.in);
        this.atmService = new ATMService(new CashDispenser(new GreedyDispenseStrategy()));
    }

    public void start() {
        System.out.println("=== Welcome to the ATM ===");
        System.out.print("Insert card (enter account number): ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        currentAccount = atmService.authenticate(accountNumber, pin);
        if (currentAccount == null) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }

        showMenu();
    }

    private void showMenu() {
        while (true) {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Cash");
            System.out.println("3. Withdraw Cash");
            System.out.println("4. Exit");
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
                    System.out.println("Thank you for using our ATM!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
