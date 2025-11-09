package com.atm.app;

import com.atm.model.Account;
import com.atm.service.ATMService;
import com.atm.service.AccountService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountService accountService = new AccountService();
        ATMService atmService = new ATMService();

        System.out.println("=== Welcome to the ATM ===");
        System.out.print("Insert card (enter account number): ");
        int accNum = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        Account acc = accountService.authenticate(accNum, pin);
        if (acc == null) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }

        while (true) {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. View Balance");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    atmService.viewBalance(acc);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    int withdrawAmount = sc.nextInt();
                    atmService.withdraw(acc, withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = sc.nextDouble();
                    atmService.deposit(acc, depositAmount);
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
