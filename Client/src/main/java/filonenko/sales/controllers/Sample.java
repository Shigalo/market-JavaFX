package filonenko.sales.controllers;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.services.UserService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Sample {

    public Button log;
    public MenuItem usersMenu;
    public MenuItem productMenu;
    public Button profile;

    public TextField login;
    public PasswordField password;
    public Button loginButton;
    public Button registration;
    public MenuItem salesMenu;

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, salesMenu, log, profile);
        thisEventHandlers();
    }

    private void thisEventHandlers() {
        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат входа");
                alert.setHeaderText(null);
                UserService.login(login.getText(), password.getText());

                if (CurrentUser.getCurrentUser() != null) {
                    alert.setContentText("Добро пожаловать " + CurrentUser.getCurrentUser().getName());
                    alert.showAndWait();

                    Stage stage = (Stage)log.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/users.fxml"));
                    Parent root = null;
                    try { root = fxmlLoader.load(); } catch (IOException ignored){}
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                else {
                    alert.setContentText("Пользователь не найден!\n" +
                            "Неверный логин или пароль.");
                    password.setText("");
                    alert.showAndWait();
                }
            }
        });

        registration.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage)log.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/registration.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException ignored){}
                stage.setScene(new Scene(root));
                stage.show();
            }
        });
    }
}
