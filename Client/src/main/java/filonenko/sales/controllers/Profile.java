package filonenko.sales.controllers;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.services.UserService;
import filonenko.sales.services.VerificationService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
        use.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Изменение имени");
            dialog.setHeaderText(null);
            dialog.setContentText("Новое имя:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newName -> {
                UserService.editName(newName);
                name.setText(CurrentUser.getCurrentUser().getName());
            });
        });

        edit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            while (!passwordDialog());
        });
    }

    private boolean passwordDialog() {
        Dialog<Pair<PasswordField, PasswordField>> dialog = new Dialog<>();
        dialog.setTitle("Изменение паролья");

        AtomicBoolean res = new AtomicBoolean(false);
        ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField passwordField = new PasswordField();
        PasswordField passwordConfirm = new PasswordField();

        grid.add(new Label("Введите пароль:"), 0, 0);
        grid.add(passwordField, 1, 0);
        grid.add(new Label("Повторите пароль:"), 0, 1);
        grid.add(passwordConfirm, 1, 1);

        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Pair<>(passwordField, passwordConfirm);
            }
            if (dialogButton == cancelButtonType) {
                res.set(true);
            }
            return null;
        });

        Optional<Pair<PasswordField, PasswordField>> result = dialog.showAndWait();

        result.ifPresent(userPassword -> {
            if(VerificationService.passwordVerification(userPassword.getKey(), new Alert(null))) {





                System.out.println("good password"); ////Метод запроса





                res.set(true);
            }
            else {
                System.out.println("Bad");
                res.set(false);
            }

        });
        return res.get();
    }
}
