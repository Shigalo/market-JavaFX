package filonenko.sales.controllers;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class Profile {

    public Button log;
    public MenuItem usersMenu;
    public MenuItem hardwareMenu;
    public Button profile;

    public Label login;
    public Button use;
    public Label user;
    public Label name;
    public Button edit;

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, hardwareMenu, log, profile);

        login.setText(CurrentUser.getCurrentUser().getLogin());
        name.setText(CurrentUser.getCurrentUser().getName());
        user.setText(CurrentUser.getCurrentUser().getName());

        thisEventHandlers();
    }

    private void thisEventHandlers() {
        use.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Изменение имени");
                alert.setHeaderText(null);
                final TextField nameField = new TextField();
                alert.getDialogPane().setContent(nameField);
                nameField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        UserService.editName(nameField.getText());
                        name.setText(CurrentUser.getCurrentUser().getName());
                        alert.close();
                    }
                });
                ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.YES);
                alert.getDialogPane().getButtonTypes().add(new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));
                alert.getDialogPane().getButtonTypes().add(buttonTypeOK);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == buttonTypeOK) {
                    UserService.editName(nameField.getText());
                    name.setText(CurrentUser.getCurrentUser().getName());
                }
            }
        });

        edit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Изменение пароля");
                alert.setHeaderText(null);
                final PasswordField passwordField = new PasswordField();
                alert.getDialogPane().setContent(passwordField);
                final PasswordField passwordConfirm = new PasswordField();
                alert.getDialogPane().setContent(passwordConfirm);
                passwordField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        UserService.editPassword(passwordField.getText(), passwordConfirm.getText());
                        alert.close();
                    }
                });
                passwordConfirm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        UserService.editPassword(passwordField.getText(), passwordConfirm.getText());
                        alert.close();
                    }
                });
                ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.YES);
                alert.getDialogPane().getButtonTypes().add(new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));
                alert.getDialogPane().getButtonTypes().add(buttonTypeOK);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == buttonTypeOK) {
                    UserService.editPassword(passwordField.getText(), passwordConfirm.getText());
                }
            }
        });
    }
}
