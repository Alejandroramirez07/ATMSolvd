package com.atm.ui;

import com.atm.facade.ATMFacade;
import com.atm.model.Account;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private final VBox root;
    private final ATMFacade facade = new ATMFacade();

    public LoginView(Stage stage) {
        root = new VBox(10);
        root.setPadding(new Insets(20));

        Label lblTitle = new Label("Welcome to Java ATM");
        TextField txtAccount = new TextField();
        txtAccount.setPromptText("Account Number");

        PasswordField txtPin = new PasswordField();
        txtPin.setPromptText("PIN");

        Button btnLogin = new Button("Login");
        Label lblMessage = new Label();

        btnLogin.setOnAction(e -> {
            String acc = txtAccount.getText().trim();
            String pin = txtPin.getText().trim();

            Account account = facade.authenticate(acc, pin);

            if (account != null) {
                new DashboardView(stage, account, facade);
            } else {
                lblMessage.setText("Invalid credentials. Try again.");
            }
        });

        root.getChildren().addAll(lblTitle, txtAccount, txtPin, btnLogin, lblMessage);
    }

    public VBox getRoot() {
        return root;
    }
}
