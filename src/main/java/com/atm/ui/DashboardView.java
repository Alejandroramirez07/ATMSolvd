package com.atm.ui;

import com.atm.facade.ATMFacade;
import com.atm.model.Account;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {

    public DashboardView(Stage stage, Account account, ATMFacade facade) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label lblBalance = new Label("Balance: $" + account.getBalance());

        // --- Deposit Section ---
        TextField txtDeposit = new TextField();
        txtDeposit.setPromptText("Deposit amount");

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(txtDeposit.getText());
                facade.deposit(account, amount);
                lblBalance.setText("Balance: $" + account.getBalance());
                showAlert(Alert.AlertType.INFORMATION, "Deposit Successful", "Deposited $" + amount);
                txtDeposit.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid number.");
            }
        });

        // --- Withdraw Section ---
        TextField txtWithdraw = new TextField();
        txtWithdraw.setPromptText("Withdraw amount");

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            try {
                int amount = Integer.parseInt(txtWithdraw.getText());
                String result = facade.withdraw(account, amount);  // returns denomination breakdown
                lblBalance.setText("Balance: $" + account.getBalance());
                showAlert(Alert.AlertType.INFORMATION, "Cash Dispensed", result);
                txtWithdraw.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid number.");
            }
        });

        // --- Exit ---
        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> stage.close());

        root.getChildren().addAll(lblBalance, txtDeposit, btnDeposit, txtWithdraw, btnWithdraw, btnExit);

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("ATM Dashboard");
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
