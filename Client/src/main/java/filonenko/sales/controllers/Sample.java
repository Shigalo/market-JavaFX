package filonenko.sales.controllers;

import filonenko.sales.app.MenuEventsHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class Sample {
    public Button exit;

    public MenuItem usersMenu;
    public MenuItem hardwareMenu;

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, hardwareMenu, exit);
    }
}
