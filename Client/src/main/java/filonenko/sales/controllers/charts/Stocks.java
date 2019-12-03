package filonenko.sales.controllers.charts;

import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Storage;
import filonenko.sales.services.StorageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;

public class Stocks {
    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public PieChart pieChart;
    public Button table;


    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
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
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("dataTables/storage"));
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
}

