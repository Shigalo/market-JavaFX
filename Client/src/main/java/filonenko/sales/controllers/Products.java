package filonenko.sales.controllers;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.Product;
import filonenko.sales.services.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.scene.input.MouseButton.PRIMARY;

public class Products {
    public MenuItem usersMenu;
    public MenuItem productMenu;
    public Button log;
    public Button profile;

    public TableColumn<Product, String> name;
    public TableColumn<Product, String> firm;
    public TableView<Product> table;
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private List<Boolean> selected = new ArrayList<>();

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, log, profile);
        name.setSortable(false);
        firm.setSortable(false);
        thisEventHandlers();

        List<Product> productList = ProductService.getAllProducts();
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        firm.setCellValueFactory(new PropertyValueFactory<Product, String>("Firm"));
        products.addAll(productList);
        table.setItems(products);
        table.setPrefHeight(25+productList.size()*25);
        table.setMaxHeight(200);
        for(Product ignored : productList)
            selected.add(false);
    }

    private void thisEventHandlers() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Изменить");
        edit.setOnAction(contextEvent -> {
            ProductService.editProduct(table.getSelectionModel().getSelectedItem());
            List<Product> productList = null;
            try {
                productList = ProductService.getAllProducts();
            } catch (Exception ignored) {
            }
            products.setAll(productList);
            table.setItems(products);
        });
        MenuItem delete = new MenuItem("Удалить");
        delete.setOnAction(contextEvent -> {
            ProductService.deleteProduct(table.getSelectionModel().getSelectedItem());
            List<Product> productList = null;
            try {
                productList = ProductService.getAllProducts();
            } catch (Exception ignored) {
            }
            products.setAll(productList);
            table.setPrefHeight(25 + products.size() * 25);
            table.setItems(products);
        });
        if(CurrentUser.getCurrentUser() != null) { contextMenu.getItems().addAll(edit, delete); }

        table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.hide();
            switch (event.getButton()) {
                case PRIMARY: {
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
                    break;
                }
                case SECONDARY: {
                    table.setOnContextMenuRequested(contextEvent -> contextMenu.show(table, event.getScreenX(), event.getScreenY()));
                    break;
                }
            }
        });
    }
}
