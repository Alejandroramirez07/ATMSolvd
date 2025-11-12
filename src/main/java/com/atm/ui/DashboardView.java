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

        TextField txtDeposit = new TextField();
        txtDeposit.setPromptText("Deposit amount");

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            double amount = Double.parseDouble(txtDeposit.getText());
            facade.deposit(account, amount);
            lblBalance.setText("Balance: $" + account.getBalance());
        });

        TextField txtWithdraw = new TextField();
        txtWithdraw.setPromptText("Withdraw amount");

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(txtWithdraw.getText());
            facade.withdraw(account, amount);
            lblBalance.setText("Balance: $" + account.getBalance());
        });

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> stage.close());

        root.getChildren().addAll(lblBalance, txtDeposit, btnDeposit, txtWithdraw, btnWithdraw, btnExit);

        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }
}
