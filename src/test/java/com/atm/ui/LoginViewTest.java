package com.atm.ui;

import com.atm.facade.ATMFacade;
import com.atm.model.Account;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.mockito.Mockito.verify;

@ExtendWith(ApplicationExtension.class)
public class LoginViewTest {

    private ATMFacade mockFacade;

    @Start
    public void start(Stage stage) {
        mockFacade = Mockito.mock(ATMFacade.class);

        LoginView loginView = new LoginView(stage);

        injectMockFacade(loginView, mockFacade);

        stage.setScene(new javafx.scene.Scene(loginView.getRoot(), 400, 300));
        stage.show();
    }

    @Test
    void testSuccessfulLogin(FxRobot robot) {

        Account mockAccount = Mockito.mock(Account.class);
        Mockito.when(mockFacade.authenticate("123456", "0000")).thenReturn(mockAccount);

        robot.clickOn(".text-field").write("123456");
        robot.clickOn(".password-field").write("0000");
        robot.clickOn("Login");

        robot.sleep(1000);

        verify(mockFacade).authenticate("123456", "0000");
    }

    @Test
    void testFailedLogin(FxRobot robot) {
        Mockito.when(mockFacade.authenticate("999999", "1111")).thenReturn(null);

        robot.clickOn(".text-field").write("999999");
        robot.clickOn(".password-field").write("1111");
        robot.clickOn("Login");

        robot.sleep(2000);
        verify(mockFacade).authenticate("999999", "1111");
    }

    private void injectMockFacade(LoginView loginView, ATMFacade facade) {
        try {
            java.lang.reflect.Field facadeField = LoginView.class.getDeclaredField("facade");
            facadeField.setAccessible(true);
            facadeField.set(loginView, facade);
        } catch (Exception e) {
            System.err.println("Warning: Could not inject mock facade: " + e.getMessage());
        }
    }
}