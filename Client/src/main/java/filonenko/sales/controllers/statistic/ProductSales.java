package filonenko.sales.controllers.statistic;

import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import filonenko.sales.services.SaleService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.List;

public class ProductSales {

    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public TableView<Sale> table;
    public TableColumn<Sale, LocalDate> date;
    public TableColumn<Sale, Integer> quantity;
    public TableColumn<Sale, String>  seller;
    public Label product;
    private ObservableList<Sale> sales = FXCollections.observableArrayList();
    public Button chart;
    public Button back;

    public static Product selectedProduct;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
        thisEventHandlers();
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        seller.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getUser().getName()));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        table.setMaxHeight(200);
        System.out.println("Список продаж продукта " + selectedProduct.getName() + " : " + selectedProduct.getFirm());
        product.setText("Список продаж продукта " + selectedProduct.getName() + " : " + selectedProduct.getFirm());
        tableUpdate();
    }

    private void thisEventHandlers() {
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("dataTables/products"));
        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("charts/productSales"));
    }

    private void tableUpdate() {
        try {
            List<Sale> saleList = SaleService.getSales(selectedProduct);
            sales.setAll(saleList);
            table.setItems(sales);
            table.setPrefHeight(25+saleList.size()*25);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
