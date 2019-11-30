package filonenko.sales.controllers;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.services.UserService;
import filonenko.sales.services.VerificationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class Profile {

    public Button log;
    public MenuItem usersMenu;
    public MenuItem productMenu;
    public MenuItem salesMenu;
    public MenuItem storageMenu;
    public Button profile;

    public Label login;
    public Button use;
    public Label user;
    public Label name;
    public Button edit;
    public Button remove;

    @FXML
    private void initialize() {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, salesMenu, storageMenu, log, profile);

        login.setText(CurrentUser.getCurrentUser().getLogin());
        name.setText(CurrentUser.getCurrentUser().getName());
        user.setText(CurrentUser.getCurrentUser().getName());

        thisEventHandlers();
    }

    private void thisEventHandlers() {
        remove.setVisible(CurrentUser.getCurrentUser().getAccess() != 1);
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

        remove.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText(null);
            alert.setContentText("Уверены, что хотите удалить свой акканут?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                UserService.remove();
                Stage stage = (Stage)log.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/sample.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e) { e.printStackTrace(); }
                stage.setScene(new Scene(root));
                stage.show();
            }
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат смены пароля");
            alert.setHeaderText(null);
            res.set(UserService.editPassword(userPassword.getKey(), userPassword.getValue(), alert));
            alert.showAndWait();
        });
        return res.get();
    }
}
