package filonenko.sales.apps;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuEventsHandler {

    static Button log = null;

    public static void eventHandlers(MenuItem usersMenu, MenuItem productMenu, MenuItem salesMenu, Button log, Button profile) {
        MenuEventsHandler.log = log;

        usersMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                MenuEventsHandler.log.getScene();
                Stage stage = (Stage)MenuEventsHandler.log.getScene().getWindow();
//                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/users.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
//                stage = new Stage();
//                stage.initModality(Modality.APPLICATION_MODAL);
//                stage.setTitle("Окно пользователей");
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

        productMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage)MenuEventsHandler.log.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/products.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException ignored){}
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

        salesMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage)MenuEventsHandler.log.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/sales.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException ignored){}
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

        if(CurrentUser.getCurrentUser() == null) {
            log.setText("Вход");
            profile.setVisible(false);
        }
        else {
            log.setText("Выход");
            profile.setVisible(true);

            profile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Stage stage = (Stage)MenuEventsHandler.log.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/profile.fxml"));
                    Parent root = null;
                    try { root = fxmlLoader.load(); } catch (IOException ignored){}
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            });
        }

        log.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(CurrentUser.getCurrentUser() != null)  CurrentUser.setCurrentUser(null);
//                MenuEventsHandler.log.getScene();
                Stage stage = (Stage)MenuEventsHandler.log.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/sample.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException ignored){}
                stage.setScene(new Scene(root));
                stage.show();
            }
        });
    }
}
