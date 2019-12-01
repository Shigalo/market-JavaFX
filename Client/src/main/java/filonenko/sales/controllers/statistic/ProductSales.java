package filonenko.sales.controllers.statistic;

import filonenko.sales.apps.Main;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import filonenko.sales.services.SaleService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ProductSales {
    public MenuItem usersMenu;
    public MenuItem productMenu;
    public MenuItem salesMenu;
    public MenuItem storageMenu;
    public Button log;
    public Button profile;

    public TableView<Sale> table;
    public TableColumn<Sale, LocalDate> date;
    public TableColumn<Sale, Integer> quantity;
    public TableColumn<Sale, String>  seller;
    public Label product;
    public LineChart salesLineChart;
    private ObservableList<Sale> sales = FXCollections.observableArrayList();
    public Button chart;
    public Button back;

    public static Product selectedProduct;

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(usersMenu, productMenu, salesMenu, storageMenu, log, profile);
        date.setSortable(false);
        quantity.setSortable(false);
        seller.setSortable(false);
        thisEventHandlers();
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        seller.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getUser().getName()));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        table.setMaxHeight(200);
        product.setText("Список продаж продукта " + selectedProduct.getName() + " : " + selectedProduct.getFirm());
        tableUpdate();
    }

    private void thisEventHandlers() {
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> goBack());
        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> show());
    }

    private void tableUpdate() {
        try {
            List<Sale> saleList = SaleService.getSales(selectedProduct);
            sales.setAll(saleList);
            table.setItems(sales);
            table.setPrefHeight(25+saleList.size()*25);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void goBack() {
        FXMLLoader fxmlLoader = new FXMLLoader(SaleService.class.getResource("/FXML/dataTables/products.fxml"));
        Parent root = null;
        try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
        Main.primaryStage.getScene().setRoot(root);
        Main.primaryStage.show();
    }
    private void show() {
        FXMLLoader fxmlLoader = new FXMLLoader(SaleService.class.getResource("/FXML/charts/productSales.fxml"));
        Parent root = null;
        try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
        Main.primaryStage.getScene().setRoot(root);
        Main.primaryStage.show();
    }
}
