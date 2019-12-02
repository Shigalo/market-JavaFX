package filonenko.sales.controllers.charts;

import filonenko.sales.apps.Main;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import filonenko.sales.entities.Storage;
import filonenko.sales.services.SaleService;
import filonenko.sales.services.StorageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.*;

import static filonenko.sales.controllers.statistic.ProductSales.selectedProduct;

public class Stocks {
    public Button log;
    public Button profile;
    public Menu data;
    public Menu charts;

    public PieChart pieChart;
    public Button table;


    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(data, charts, log, profile);
        thisEventHandlers();
        chartProperties();
        chartUpdate();
    }

    private void chartProperties() {
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.LEFT);
        pieChart.setLegendVisible(false);
    }

    private void thisEventHandlers() {
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> toTable());
    }

    private void chartUpdate() {
        try {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for(Storage storage : StorageService.getStorage()) {
                PieChart.Data data = new PieChart.Data(storage.getProduct().getName(), storage.getQuantity());
                data.setName(storage.getProduct().getName() + " " + storage.getQuantity());
                pieChartData.add(data);
            }
            pieChart.getData().clear();
            pieChart.getData().addAll(pieChartData);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void toTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(SaleService.class.getResource("/FXML/dataTables/storage.fxml"));
        Parent root = null;
        try { root = fxmlLoader.load(); } catch (IOException e){ e.printStackTrace(); }
        Main.primaryStage.getScene().setRoot(root);
        Main.primaryStage.show();
    }
}

