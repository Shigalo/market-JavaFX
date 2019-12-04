package filonenko.sales.controllers.dataTables;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.User;
import filonenko.sales.services.UserService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Users {

    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public TableColumn<User, String> login;
    public TableColumn<User, String> name;
    public TableColumn<User, String> access;
    public TableView<User> table;
    private ObservableList<User> users = FXCollections.observableArrayList();
    private List<Boolean> selected = new ArrayList<>();

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
        thisEventHandlers();

        List<User> userList = UserService.getAllUsers();
        login.setCellValueFactory(new PropertyValueFactory<>("Login"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        access.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccess() == 1 ? "Администратор" : "Пользователь"));
        users.addAll(userList);
        table.setItems(users);
        table.setPrefHeight(25+userList.size()*25);
        table.setMaxHeight(200);
        for(User ignored : userList)
            selected.add(false);
    }

    private void thisEventHandlers() {
        try {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem setRole = new MenuItem("Изменить доступ");
            setRole.setOnAction(contextEvent -> UserService.setRole(table.getSelectionModel().getSelectedItem()));
            if(CurrentUser.getCurrentUser() != null)
                if (CurrentUser.getCurrentUser().getAccess() == 2)
                    contextMenu.getItems().addAll(setRole);
            table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                contextMenu.hide();
                switch (event.getButton()) {
                    case SECONDARY: {
                        if (CurrentUser.getCurrentUser().getAccess() == 2)
                            table.setOnContextMenuRequested(contextEvent -> contextMenu.show(table, event.getScreenX(), event.getScreenY()));
                        break;
                    }
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
    }
}
