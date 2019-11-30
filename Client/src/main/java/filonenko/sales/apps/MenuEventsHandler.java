package filonenko.sales.apps;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuEventsHandler {


    public static void eventHandlers(MenuItem usersMenu, MenuItem productMenu, MenuItem salesMenu, MenuItem storageMenu, Button log, Button profile) {

        usersMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/users.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                Main.primaryStage.getScene().setRoot(root);
                Main.primaryStage.show();
            }
        });

        productMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/products.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                Main.primaryStage.getScene().setRoot(root);
                Main.primaryStage.show();
            }
        });

        salesMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/sales.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                Main.primaryStage.getScene().setRoot(root);
                Main.primaryStage.show();
            }
        });

        storageMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/storage.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                Main.primaryStage.getScene().setRoot(root);
                Main.primaryStage.show();
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
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/profile.fxml"));
                    Parent root = null;
                    try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                    Main.primaryStage.getScene().setRoot(root);
                    Main.primaryStage.show();
                }
            });
        }

        log.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(CurrentUser.getCurrentUser() != null)  CurrentUser.setCurrentUser(null);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/sample.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                Main.primaryStage.getScene().setRoot(root);
                Main.primaryStage.show();
            }
        });
    }
}
