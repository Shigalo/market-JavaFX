package filonenko.sales.controllers;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.Main;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.services.UserService;
import filonenko.sales.services.VerificationService;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Registration {

    public MenuItem usersMenu;
    public MenuItem productMenu;
    public MenuItem salesMenu;
    public MenuItem storageMenu;

    public TextField name;
    public TextField login;
    public PasswordField password;
    public PasswordField passwordConfirm;
    public Button registration;
    public Button log;
    public Button cancel;

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, salesMenu, storageMenu, log, new Button());
        thisEventHandlers();
    }

    private void thisEventHandlers() {
        registration.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат регистрации");
                alert.setHeaderText(null);

                if (VerificationService.fullVerification(login, password, passwordConfirm, name, alert)) {
                    UserService.registration(login.getText(), password.getText(), name.getText());
                    if (CurrentUser.getCurrentUser() != null) {
                        alert.setContentText("Успешная регистрация\n" +
                                "Вход в систему\n" +
                                "Добро пожаловать " + CurrentUser.getCurrentUser().getName());
                        alert.showAndWait();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/users.fxml"));
                        Parent root = null;
                        try {
                            root = fxmlLoader.load();
                        } catch (IOException e) { e.printStackTrace(); }
                        Main.primaryStage.getScene().setRoot(root);
                        Main.primaryStage.show();
                    } else {
                        alert.setContentText("Логин не доступен!");
                        login.setText("");
                        alert.showAndWait();
                    }
                }
                else alert.showAndWait();
            }
        });

        cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/sample.fxml"));
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) { e.printStackTrace(); }
                Main.primaryStage.getScene().setRoot(root);
                Main.primaryStage.show();
            }
        });
    }
}
