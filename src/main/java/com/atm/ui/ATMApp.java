package com.atm.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ATMApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView(primaryStage);
        primaryStage.setTitle("ATM Machine");
        primaryStage.setScene(new Scene(loginView.getRoot(), 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
