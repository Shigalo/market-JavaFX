package filonenko.sales.controllers;

import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.User;
import filonenko.sales.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Users {

    public MenuItem usersMenu;
    public MenuItem productMenu;
    public MenuItem salesMenu;
    public MenuItem storageMenu;
    public Button log;
    public Button profile;

    public TableColumn<User, String> login;
    public TableColumn<User, String> name;
    public TableColumn<User, String> access;
    public TableView<User> table;
    private ObservableList<User> users = FXCollections.observableArrayList();
    private List<Boolean> selected = new ArrayList<>();

    @FXML
    private void initialize() throws Exception {
        System.out.println("us");
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, salesMenu, storageMenu, log, profile);
        login.setSortable(false);
        name.setSortable(false);
        access.setSortable(false);

        List<User> userList = UserService.getAllUsers();
        login.setCellValueFactory(new PropertyValueFactory<>("Login"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        access.setCellValueFactory(new PropertyValueFactory<>("Access"));
        users.addAll(userList);
        table.setItems(users);
        table.setPrefHeight(25+userList.size()*25);
        table.setMaxHeight(200);
        for(User ignored : userList)
            selected.add(false);
    }
}
