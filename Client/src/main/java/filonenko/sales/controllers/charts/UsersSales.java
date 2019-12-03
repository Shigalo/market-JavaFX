package filonenko.sales.controllers.charts;

import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.User;
import filonenko.sales.services.GuaranteeService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersSales {
    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public Button table;
    public BarChart<String, Number> barChart;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
        thisEventHandlers();
        chartUpdate();
        chartProperties();
    }

    private void chartProperties() {
        barChart.getXAxis().setLabel("Сотрудники");
        barChart.getYAxis().setLabel("Количество");

    }

    private void thisEventHandlers() {
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("statistic/usersSales"));
    }

    private void chartUpdate() {
        try {
            try {
                List<Guarantee> guaranteeList = GuaranteeService.getGuaranties();
                Map<User, Data> userDataMap = new HashMap<>();
                for(Guarantee guarantee : guaranteeList) {
                    User user = guarantee.getSale().getUser();
                    if(!userDataMap.containsKey(user)) userDataMap.put(user, new Data(user));
                    userDataMap.get(user).selling += 1;
                    switch (guarantee.getStatus().getId()) {
                        case 3: userDataMap.get(user).reselling += 1; break;
                        case 4: userDataMap.get(user).refund += 1; break;
                        case 5: userDataMap.get(user).repairs += 1; break;
                        default: userDataMap.get(user).cost += guarantee.getSale().getQuantity() * guarantee.getSale().getProduct().getUnit_price();
                    }
                }
                for(User user : userDataMap.keySet()) {
                    Data data = userDataMap.get(user);
                    XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
                    dataSeries.setName(data.seller.getName());

                    dataSeries.getData().add(new XYChart.Data<>("Всего сделок", data.selling));
                    dataSeries.getData().add(new XYChart.Data<>("Замен", data.reselling));
                    dataSeries.getData().add(new XYChart.Data<>("Ремонтов", data.repairs));
                    dataSeries.getData().add(new XYChart.Data<>("Возвращено", data.refund));
                    barChart.getData().add(dataSeries);
                }
            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace(); }
    }

    class Data {
        Data(User seller) {
            this.seller = seller;
        }
        User seller;
        Double cost = 0.0;
        Integer selling = 0;
        Integer refund = 0;
        Integer reselling = 0;
        Integer repairs = 0;
    }
}

