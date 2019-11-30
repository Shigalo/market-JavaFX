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
    public MenuItem salesMenu;
    public MenuItem storageMenu;
    public Button log;
    public Button profile;

    public TableView<Sale> table;
    public TableColumn<Sale, LocalDate> date;
    public TableColumn<Sale, String> product;
    public TableColumn<Sale, Integer> quantity;
    public TableColumn<Sale, String>  seller;
    private ObservableList<Sale> sales = FXCollections.observableArrayList();

    public Button add;
    private List<Boolean> selected = new ArrayList<>();

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, salesMenu, storageMenu, log, profile);
        date.setSortable(false);
        product.setSortable(false);
        quantity.setSortable(false);
        seller.setSortable(false);
        thisEventHandlers();
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        product.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProduct().getName()));
        seller.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getUser().getName()));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        table.setMaxHeight(200);
        tableUpdate();
    }

    private void thisEventHandlers() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Удалить");
        delete.setOnAction(contextEvent -> {
            if( CurrentUser.getCurrentUser().equals(table.getSelectionModel().getSelectedItem().getUser()) || CurrentUser.getCurrentUser().getAccess() == 1)
                SaleService.deleteSale(table.getSelectionModel().getSelectedItem());
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Невозможно удалить данные\nпродаж другого пользователя");
                alert.showAndWait();
            }
            tableUpdate();
        });
        MenuItem info = new MenuItem("Подробнее");
        info.setOnAction(contextEvent -> {
            SaleService.getInfo(table.getSelectionModel().getSelectedItem());
            tableUpdate();
        });
        contextMenu.getItems().addAll(info);

        if(CurrentUser.getCurrentUser() != null) {
            add.setVisible(true);
            contextMenu.getItems().addAll(delete);
            add.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                SaleService.addSale();
                tableUpdate();
            });
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
        } catch (Exception e) { e.printStackTrace(); }
    }
}
