package filonenko.sales.controllers.statistic;

import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.Sale;
import filonenko.sales.services.GuaranteeService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesProceeds {

    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public TableView<Data> table;
    public TableColumn<Data, LocalDate> date;
    public TableColumn<Data, Double> income;
    public TableColumn<Data, Double> losses;
    public TableColumn<Data, Double> profit;
    private ObservableList<Data> sales = FXCollections.observableArrayList();
    public Button chart;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
        thisEventHandlers();
        date.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().date));
        income.setCellValueFactory(data -> {
            double costValue = data.getValue().income;
            costValue = Double.parseDouble(String.format("%.2f", costValue).replace(",", "."));
            return new SimpleDoubleProperty(costValue).asObject();
        });
        losses.setCellValueFactory(data -> {
            double costValue = data.getValue().losses;
            costValue = Double.parseDouble(String.format("%.2f", costValue).replace(",", "."));
            return new SimpleDoubleProperty(costValue).asObject();
        });
        profit.setCellValueFactory(data -> {
            double costValue = data.getValue().profit;
            costValue = Double.parseDouble(String.format("%.2f", costValue).replace(",", "."));
            return new SimpleDoubleProperty(costValue).asObject();
        });
        table.setMaxHeight(200);
        tableUpdate();
    }

    private void thisEventHandlers() {
        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("charts/salesProceeds"));
    }

    private void tableUpdate() {
        try {
            List<Guarantee> guaranteeList = GuaranteeService.getGuaranties();
            Map<LocalDate, Data> dataMap = new HashMap<>();
            for(Guarantee guarantee : guaranteeList) {
                LocalDate date = guarantee.getSale().getDate();
                if(!dataMap.containsKey(date)) dataMap.put(date, new Data(date));
                dataMap.get(date).income += getCost(guarantee);
                switch (guarantee.getStatus().getId()) {
                    case 3: case 4: case 5: dataMap.get(date).losses += getCost(guarantee); break;
                    default: dataMap.get(date).profit += getCost(guarantee); break;
                }
            }
            for(LocalDate date : dataMap.keySet()) {
                Data data = dataMap.get(date);
                sales.addAll(data);
            }
            table.setItems(sales);
            table.setPrefHeight(25+dataMap.size()*25);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Double getCost(Guarantee guarantee) { return guarantee.getSale().getQuantity() * guarantee.getSale().getProduct().getUnit_price(); }

   class Data {
       public Data(LocalDate date) { this.date = date; }
       LocalDate date;
       Double income = 0.0;
       Double losses = 0.0;
       Double profit = 0.0;
   }
}
