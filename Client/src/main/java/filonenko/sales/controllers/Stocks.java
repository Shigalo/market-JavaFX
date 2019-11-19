package filonenko.sales.controllers;

import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.Storage;
import filonenko.sales.services.StorageService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Stocks {

    public MenuItem usersMenu;
    public MenuItem productMenu;
    public MenuItem salesMenu;
    public MenuItem storageMenu;
    public Button log;
    public Button profile;

    public TableView<Storage> table;
    public TableColumn<Storage, String> product;
    public TableColumn<Storage, Integer> quantity;
    private ObservableList<Storage> storage = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, salesMenu, storageMenu, log, profile);
        product.setSortable(false);
        quantity.setSortable(false);
        //        thisEventHandlers();
        product.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProduct().getName()));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        table.setMaxHeight(200);
        tableUpdate();
    }

    private void tableUpdate() {
        try {
            List<Storage> storageList = StorageService.getStorage();
            storage.setAll(storageList);
            table.setItems(storage);
            table.setPrefHeight(25+storageList.size()*25);
        } catch (Exception e) { e.printStackTrace();}
    }
}
