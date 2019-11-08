package filonenko.sales.controllers;

import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.User;
import filonenko.sales.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Users {

    public Button log;
    public MenuItem usersMenu;
    public MenuItem productMenu;
    public Button profile;

    public TableColumn<User, String> login;
    public TableColumn<User, String> name;
    public TableColumn<User, String> access;
    public TableView<User> table;
    private ObservableList<User> users = FXCollections.observableArrayList();
    private List<Boolean> selected = new ArrayList<>();

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, log, profile);
        login.setSortable(false);
        name.setSortable(false);
        access.setSortable(false);
        thisEventHandlers();

        List<User> userList = UserService.getAllUsers();
        login.setCellValueFactory(new PropertyValueFactory<User, String>("Login"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        access.setCellValueFactory(new PropertyValueFactory<User, String>("Access"));
        users.addAll(userList);
        table.setItems(users);
        table.setPrefHeight(25+userList.size()*25);
        table.setMaxHeight(200);
        for(User ignored : userList)
            selected.add(false);
    }

    private void thisEventHandlers() {
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Node n : table.lookupAll("TableRow")) {
                    if (n instanceof TableRow) {
                        TableRow row = (TableRow) n;
                        if (row.getStyle() != ("-fx-background-color: rgb(152, 251, 152);")) {
                            row.setStyle("-fx-background-color: white;");
                        }
                        if (row.getIndex() == table.getSelectionModel().getFocusedIndex()) {
                            if (!selected.get(row.getIndex())) {
                                selected.set(row.getIndex(), !selected.get(row.getIndex()));
                                row.setStyle("-fx-background-color: rgb(152, 251, 152);");
                            } else {
                                selected.set(row.getIndex(), !selected.get(row.getIndex()));
                                row.setStyle("-fx-background-color: rgb(135, 206, 235);");
                            }
                        }
                    }
                }
            }
        });
    }
}
