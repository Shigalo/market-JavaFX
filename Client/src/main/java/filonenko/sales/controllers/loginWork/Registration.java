package filonenko.sales.controllers.loginWork;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.services.UserService;
import filonenko.sales.services.VerificationService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class Registration {

    public Button log;
    public Button cancel;
    public MenuBar menuBar;

    public TextField name;
    public TextField login;
    public PasswordField password;
    public PasswordField passwordConfirm;
    public Button registration;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, new Button());
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
                        MediatorEventsHandler.changeScene("dataTables/users");
                    } else {
                        alert.setContentText("Логин не доступен!");
                        login.setText("");
                        alert.showAndWait();
                    }
                }
                else alert.showAndWait();
            }
        });

        cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("loginWork/sample"));
    }
}
