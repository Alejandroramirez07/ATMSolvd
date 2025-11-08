package com.atm.app;

import com.atm.model.Account;
import com.atm.service.AccountService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountService service = new AccountService();

        System.out.println("=== Welcome to the ATM ===");
        System.out.print("Insert card (enter account number): ");
        int accountNumber = sc.nextInt();
        System.out.print("Enter PIN: ");
        String pin = sc.next();

        Account acc = service.authenticate(accountNumber, pin);
        if (acc == null) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }

        int option;
        do {
            System.out.println("\n1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            option = sc.nextInt();

            switch (option) {
                case 1 -> System.out.println("Your balance: " + acc.getBalance());
                case 2 -> {
                    System.out.print("Enter deposit amount: ");
                    double dep = sc.nextDouble();
                    acc.setBalance(acc.getBalance() + dep);
                    service.updateBalance(acc, acc.getBalance());
                    System.out.println("Deposit successful!");
                }
                case 3 -> {
                    System.out.print("Enter withdrawal amount: ");
                    double w = sc.nextDouble();
                    if (w <= acc.getBalance()) {
                        acc.setBalance(acc.getBalance() - w);
                        service.updateBalance(acc, acc.getBalance());
                        System.out.println("Withdraw successful!");
                    } else {
                        System.out.println("Insufficient balance!");
                    }
                }
                case 4 -> System.out.println("Thank you. Goodbye!");
            }
        } while (option != 4);
    }
}
