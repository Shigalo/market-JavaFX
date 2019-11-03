package filonenko.sales.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuEventsHandler {

    static Button exit = null;

    public static void eventHandlers(MenuItem usersMenu, MenuItem hardwareMenu, Button exit) {
        MenuEventsHandler.exit = exit;

        usersMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("usersMenu event");
                MenuEventsHandler.exit.getScene();
                Stage stage = (Stage)MenuEventsHandler.exit.getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/users.fxml"));
                Parent root = null;
                try { root = (Parent) fxmlLoader.load(); } catch (IOException ignored){}
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Другая форма");
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

        hardwareMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("hardwareMenu event");
            }
        });

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
    }
}
