package filonenko.sales.controllers;

import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.Product;
import filonenko.sales.services.ProductService;
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
