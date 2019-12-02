package filonenko.sales.controllers.loginWork;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.Main;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.services.UserService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Sample {

    public Button log;
    public Button profile;
    public Menu data;
    public Menu charts;

    public TextField login;
    public PasswordField password;
    public Button loginButton;
    public Button registration;

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(data, charts, log, profile);
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

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/dataTables/users.fxml"));
                    Parent root = null;
                    try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                    Main.primaryStage.getScene().setRoot(root);
                    Main.primaryStage.show();
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/loginWork/registration.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                Main.primaryStage.getScene().setRoot(root);
                Main.primaryStage.show();
            }
        });
    }
}
