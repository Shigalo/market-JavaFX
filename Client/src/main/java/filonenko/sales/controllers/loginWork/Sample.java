package filonenko.sales.controllers.loginWork;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class Sample {

    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public TextField login;
    public PasswordField password;
    public Button loginButton;
    public Button registration;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
        thisEventHandlers();
    }

    private void thisEventHandlers() {
        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат входа");
            alert.setHeaderText(null);
            UserService.login(login.getText(), password.getText());

            if (CurrentUser.getCurrentUser() != null) {
                alert.setContentText("Добро пожаловать " + CurrentUser.getCurrentUser().getName());
                alert.showAndWait();
                MediatorEventsHandler.changeScene("dataTables/users");
            }
            else {
                alert.setContentText("Пользователь не найден!\n" +
                        "Неверный логин или пароль.");
                password.setText("");
                alert.showAndWait();
            }
        });

        registration.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("loginWork/registration"));
    }
}
