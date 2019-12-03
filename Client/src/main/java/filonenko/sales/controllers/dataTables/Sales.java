package filonenko.sales.controllers.dataTables;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.Sale;
import filonenko.sales.services.GuaranteeService;
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

public class Sales {
    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public TableView<Sale> table;
    public TableColumn<Sale, LocalDate> date;
    public TableColumn<Sale, String> product;
    public TableColumn<Sale, Integer> quantity;
    public TableColumn<Sale, String>  seller;
    private ObservableList<Sale> sales = FXCollections.observableArrayList();
    public Button add;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
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
        MenuItem change = new MenuItem("Обращение по гарантии");
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
        change.setOnAction(contextEvent -> {
            Guarantee guarantee = GuaranteeService.getGuarantee(table.getSelectionModel().getSelectedItem());
            GuaranteeService.update(guarantee);
            tableUpdate();
        });
        MenuItem info = new MenuItem("Подробнее");
        info.setOnAction(contextEvent -> {
            SaleService.getInfo(table.getSelectionModel().getSelectedItem());
            tableUpdate();
        });
        contextMenu.getItems().addAll(info);

        if(CurrentUser.getCurrentUser() != null) {
            add.setVisible(CurrentUser.getCurrentUser().getAccess() != 2);
            contextMenu.getItems().addAll(delete, change);
            add.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                SaleService.addSale();
                tableUpdate();
            });
        }
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.hide();
            switch (event.getButton()) {
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
        } catch (Exception e) { e.printStackTrace(); }
    }
}
