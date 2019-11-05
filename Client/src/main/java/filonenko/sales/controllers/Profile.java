package filonenko.sales.controllers;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MenuEventsHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
                final TextField textField = new TextField();
                alert.getDialogPane().setContent(textField);
                textField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        /////////////////Запрос\\\\\\\\\\\\\\\\


                        alert.close();
                    }
                });
                alert.getDialogPane().getButtonTypes().add(new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));
                alert.showAndWait();
            }
        });
    }
}
