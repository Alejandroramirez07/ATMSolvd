package com.atm.ui;

import com.atm.service.ATMService;
import com.atm.service.CashDispenser;
import com.atm.strategy.GreedyDispenseStrategy;

public class ATMConsole {
    private final ATMService authService;
    private final CashDispenser dispenser;

    public ATMConsole() {
        var repository = new InMemoryAccountRepository(); // or MyBatis repository
        this.authService = new ATMService(repository);
        this.dispenser = new CashDispenser(new GreedyDispenseStrategy()); // ✅ Works now
    }
    public void start() {
        System.out.println("=== Welcome to the ATM ===");
        System.out.print("Insert card (enter account number): ");
        String accNum = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        currentAccount = authService.authenticate(accNum, pin);
        if (currentAccount == null) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }

        showMenu();
    }

    private void showMenu() {
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. View Balance");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Exit");
            System.out.print("Select: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> System.out.println("Balance: $" + currentAccount.getBalance());
                case 2 -> withdraw();
                case 3 -> deposit();
                case 4 -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (currentAccount.withdraw(amount)) {
            dispenser.dispense((int) amount);
            System.out.println("New balance: $" + currentAccount.getBalance());
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        currentAccount.deposit(amount);
        System.out.println("New balance: $" + currentAccount.getBalance());
    }
}
