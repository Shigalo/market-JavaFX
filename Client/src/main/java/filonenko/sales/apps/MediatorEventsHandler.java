package filonenko.sales.apps;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MediatorEventsHandler {


    public static void eventHandlers(MenuBar menuBar, Button log, Button profile) {
        Menu data = new Menu();
        Menu charts = new Menu();
        Menu statistic = new Menu();
        menuBar.getMenus().addAll(data, charts, statistic);

        setLog(log, profile);
        setData(data);
        setCharts(charts);
        setStatistic(statistic);

        charts.setVisible(CurrentUser.getCurrentUser() != null);
        statistic.setVisible(CurrentUser.getCurrentUser() != null);
    }

    private static void setStatistic(Menu statistic) {
        MenuItem sales = new MenuItem("Продажи");
        MenuItem proceeds = new MenuItem("Доходы");
        statistic.getItems().addAll(sales, proceeds);
        statistic.setText("Статистика");

        sales.setOnAction(event -> changeScene("statistic/usersSales"));
        proceeds.setOnAction(event -> changeScene("statistic/salesProceeds"));
    }

    private static void setCharts(Menu charts) {
        MenuItem storage = new MenuItem("Запасы");
        MenuItem sales = new MenuItem("Продажи");
        MenuItem proceeds = new MenuItem("Доходы");
        charts.getItems().addAll(storage, sales, proceeds);
        charts.setText("Графики");

        storage.setOnAction(event -> changeScene("charts/storage"));
        sales.setOnAction(event -> changeScene("charts/usersSales"));
        proceeds.setOnAction(event -> changeScene("charts/salesProceeds"));
    }

    private static void setData(Menu data) {
        MenuItem usersMenu = new MenuItem("Пользователи");
        MenuItem productMenu = new MenuItem("Продукция");
        MenuItem salesMenu = new MenuItem("Продажи");
        MenuItem storageMenu = new MenuItem("Склад");
        salesMenu.setVisible(CurrentUser.getCurrentUser() != null);
        storageMenu.setVisible(CurrentUser.getCurrentUser() != null);
        data.getItems().addAll(usersMenu, productMenu, salesMenu, storageMenu);
        data.setText("Таблицы");

        usersMenu.setOnAction(event -> changeScene("dataTables/users"));
        productMenu.setOnAction(event -> changeScene("dataTables/products"));
        salesMenu.setOnAction(event -> changeScene("dataTables/sales"));
        storageMenu.setOnAction(event -> changeScene("dataTables/storage"));
    }

    private static void setLog(Button log, Button profile) {
        if(CurrentUser.getCurrentUser() == null) {
            log.setText("Вход");
            profile.setVisible(false);
        }
        else {
            log.setText("Выход");
            profile.setVisible(CurrentUser.getCurrentUser().getAccess() != 2);

            profile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeScene("loginWork/profile"));
        }

        log.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(CurrentUser.getCurrentUser() != null)  CurrentUser.setCurrentUser(null);
            changeScene("loginWork/sample");
        });
    }

    public static void changeScene(String address) {
        FXMLLoader fxmlLoader = new FXMLLoader(MediatorEventsHandler.class.getResource("/FXML/" + address + ".fxml"));
        Parent root = null;
        try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
        Main.primaryStage.getScene().setRoot(root);
        Main.primaryStage.show();
    }

}
