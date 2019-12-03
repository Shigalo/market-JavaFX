package filonenko.sales.controllers.charts;

import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.services.GuaranteeService;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesProceeds {
    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public Button table;
    public AreaChart proceeds;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
        thisEventHandlers();
        chartUpdate();
        chartProperties();
    }

    private void chartProperties() {
        proceeds.getXAxis().setLabel("Дата");
        proceeds.getYAxis().setLabel("Стоимость");
    }

    private void thisEventHandlers() { table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("statistic/salesProceeds")); }

    private void chartUpdate() {
        try {
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
                XYChart.Series<String, Double> incomeSeries = new XYChart.Series<>(); incomeSeries.setName("Доход");
                XYChart.Series<String, Double> lossesSeries = new XYChart.Series<>(); lossesSeries.setName("Потери");
                XYChart.Series<String, Double> profitSeries = new XYChart.Series<>(); profitSeries.setName("Прибыль");
                for(LocalDate date : dataMap.keySet()) {
                    Data data = dataMap.get(date);
                    incomeSeries.getData().add(new XYChart.Data<>(String.valueOf(date), data.income));
                    lossesSeries.getData().add(new XYChart.Data<>(String.valueOf(date), data.losses));
                    profitSeries.getData().add(new XYChart.Data<>(String.valueOf(date), data.profit));
                }
                proceeds.getData().addAll(incomeSeries, lossesSeries, profitSeries);
            } catch (Exception e) { e.printStackTrace(); }
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

