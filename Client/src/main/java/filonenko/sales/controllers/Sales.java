package filonenko.sales.controllers;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import filonenko.sales.services.ProductService;
import filonenko.sales.services.SaleService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sales {
    public MenuItem usersMenu;
    public MenuItem productMenu;
    public Button log;
    public Button profile;

    //    public TableColumn<Product, String> name;
//    public TableColumn<Product, String> firm;
//    public TableColumn<Product, Double> unit_price;
    public TableView<Sale> table;
    public TableColumn<Sale, LocalDate> date;
    public TableColumn<Sale, String> product;
    public TableColumn<Sale, Integer> quantity;

    public Button add;
    public MenuItem salesMenu;
    private ObservableList<Sale> sales = FXCollections.observableArrayList();
    private List<Boolean> selected = new ArrayList<>();

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, salesMenu, log, profile);
        date.setSortable(false);
        product.setSortable(false);
        quantity.setSortable(false);
        thisEventHandlers();
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        product.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProduct().getName()));



        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        table.setMaxHeight(200);
        tableUpdate();

    }

    private void thisEventHandlers() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Изменить");
        edit.setOnAction(contextEvent -> {
//            ProductService.editProduct(table.getSelectionModel().getSelectedItem());
            tableUpdate();
        });
        MenuItem delete = new MenuItem("Удалить");
        delete.setOnAction(contextEvent -> {
//            ProductService.deleteProduct(table.getSelectionModel().getSelectedItem());
            tableUpdate();
        });

        add.setVisible(false);
        if(CurrentUser.getCurrentUser() != null) {
            contextMenu.getItems().addAll(edit, delete);
            if(CurrentUser.getCurrentUser().getAccess() == 1) { add.setVisible(true);
                add.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//                    ProductService.addProduct();
                    tableUpdate();
                }); }
        }
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

    private void tableUpdate() {
        try {
            List<Sale> saleList = SaleService.getAllSales();
            sales.setAll(saleList);
            table.setItems(sales);
            table.setPrefHeight(25+saleList.size()*25);
            selected.clear();
            for(Sale ignored : saleList)
                selected.add(false);
            for (Node n : table.lookupAll("TableRow")) {
                if (n instanceof TableRow) {
                    TableRow row = (TableRow) n;
                    row.setStyle("-fx-background-color: white;");
                }
            }
        } catch (Exception ignored) { }
    }
}
