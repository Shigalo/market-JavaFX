package filonenko.sales.apps;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MenuEventsHandler {


    public static void eventHandlers(Menu data, Menu charts, Button log, Button profile) {
        setLog(log, profile);
        setData(data);
        setCharts(charts);
    }

    private static void setCharts(Menu charts) {
        MenuItem storage = new MenuItem("Складские запасы");
        MenuItem sales = new MenuItem("Продажи");
        charts.getItems().addAll(storage, sales);
        charts.setText("Графики");

        storage.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuEventsHandler.class.getResource("/FXML/charts/storage.fxml"));
            Parent root = null;
            try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
            Main.primaryStage.getScene().setRoot(root);
            Main.primaryStage.show();
        });
        sales.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuEventsHandler.class.getResource("/FXML/statistic/usersSales.fxml"));
            Parent root = null;
            try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
            Main.primaryStage.getScene().setRoot(root);
            Main.primaryStage.show();
        });
    }

    private static void setData(Menu data) {
        MenuItem usersMenu = new MenuItem("Пользователи");
        MenuItem productMenu = new MenuItem("Продукция");
        MenuItem salesMenu = new MenuItem("Продажи");
        MenuItem storageMenu = new MenuItem("Склад");
        data.getItems().addAll(usersMenu, productMenu, salesMenu, storageMenu);
        data.setText("Таблицы");

        usersMenu.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuEventsHandler.class.getResource("/FXML/dataTables/users.fxml"));
            Parent root = null;
            try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
            Main.primaryStage.getScene().setRoot(root);
            Main.primaryStage.show();
        });
        productMenu.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuEventsHandler.class.getResource("/FXML/dataTables/products.fxml"));
            Parent root = null;
            try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
            Main.primaryStage.getScene().setRoot(root);
            Main.primaryStage.show();
        });
        salesMenu.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuEventsHandler.class.getResource("/FXML/dataTables/sales.fxml"));
            Parent root = null;
            try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
            Main.primaryStage.getScene().setRoot(root);
            Main.primaryStage.show();
        });
        storageMenu.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuEventsHandler.class.getResource("/FXML/dataTables/storage.fxml"));
            Parent root = null;
            try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
            Main.primaryStage.getScene().setRoot(root);
            Main.primaryStage.show();
        });
    }

    private static void setLog(Button log, Button profile) {
        if(CurrentUser.getCurrentUser() == null) {
            log.setText("Вход");
            profile.setVisible(false);
        }
        else {
            log.setText("Выход");
            profile.setVisible(true);

            profile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                FXMLLoader fxmlLoader = new FXMLLoader(MenuEventsHandler.class.getResource("/FXML/loginWork/profile.fxml"));
                Parent root = null;
                try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
                Main.primaryStage.getScene().setRoot(root);
                Main.primaryStage.show();
            });
        }

        log.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(CurrentUser.getCurrentUser() != null)  CurrentUser.setCurrentUser(null);
            FXMLLoader fxmlLoader = new FXMLLoader(MenuEventsHandler.class.getResource("/FXML/loginWork/sample.fxml"));
            Parent root = null;
            try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
            Main.primaryStage.getScene().setRoot(root);
            Main.primaryStage.show();
        });
    }
}
