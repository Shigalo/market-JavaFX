package filonenko.sales.controllers.dataTables;

import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.Main;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.Storage;
import filonenko.sales.services.SaleService;
import filonenko.sales.services.StorageService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class Stocks {

    public Button log;
    public Button profile;
    public Menu data;
    public Menu charts;

    public TableView<Storage> table;
    public TableColumn<Storage, String> product;
    public TableColumn<Storage, Integer> quantity;
    public TableColumn<Storage, Double> cost;
    private ObservableList<Storage> storage = FXCollections.observableArrayList();
    public Button storageChart;

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(data, charts, log, profile);
        product.setSortable(false);
        quantity.setSortable(false);
        thisEventHandlers();
        product.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProduct().getName()));
        cost.setCellValueFactory(data -> {
            double costValue = data.getValue().getProduct().getUnit_price() * data.getValue().getQuantity();
            costValue = Double.parseDouble(String.format("%.2f", costValue).replace(",", "."));
            return new SimpleDoubleProperty(costValue).asObject();
        });
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

    private void thisEventHandlers() {
        try {
            storageChart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> toChart());

            ContextMenu contextMenu = new ContextMenu();
            MenuItem replenish = new MenuItem("Пополнить");
            replenish.setOnAction(contextEvent -> {
                StorageService.replenish(table.getSelectionModel().getSelectedItem());
                tableUpdate();
            });

            if (CurrentUser.getCurrentUser() != null) {
                contextMenu.getItems().addAll(replenish);
            }
            table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                contextMenu.hide();
                switch (event.getButton()) {
                    case SECONDARY: {
                        if (CurrentUser.getCurrentUser().getAccess() == 1)
                            table.setOnContextMenuRequested(contextEvent -> contextMenu.show(table, event.getScreenX(), event.getScreenY()));
                        break;
                    }
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void toChart() {
        FXMLLoader fxmlLoader = new FXMLLoader(SaleService.class.getResource("/FXML/charts/storage.fxml"));
        Parent root = null;
        try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
        Main.primaryStage.getScene().setRoot(root);
        Main.primaryStage.show();
    }
}
