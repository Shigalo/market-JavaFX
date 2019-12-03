package filonenko.sales.controllers.statistic;

import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.User;
import filonenko.sales.services.GuaranteeService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersSales {

    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public TableView<Data> table;
    public TableColumn<Data, String> seller;
    public TableColumn<Data, Double> cost;
    public TableColumn<Data, Integer> selling;
    public TableColumn<Data, Integer> refund;
    public TableColumn<Data, Integer> reselling;
    public TableColumn<Data, Integer> repairs;
    private ObservableList<Data> sales = FXCollections.observableArrayList();
    public Button chart;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
        thisEventHandlers();
        seller.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().seller.getName()));
        cost.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().cost).asObject());
        selling.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().selling).asObject());
        refund.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().refund).asObject());
        reselling.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().reselling).asObject());
        repairs.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().repairs).asObject());
        table.setMaxHeight(210);
        tableUpdate();
    }

    private void thisEventHandlers() {
        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("charts/usersSales"));
    }

    private void tableUpdate() {
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
                    default: userDataMap.get(user).cost += getCost(guarantee);
                }
            }
            for(User user : userDataMap.keySet()) {
                Data data = userDataMap.get(user);
                sales.addAll(data);
                table.setItems(sales);
            }
            table.setPrefHeight(60+userDataMap.size()*25);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Double getCost(Guarantee guarantee) { return guarantee.getSale().getQuantity() * guarantee.getSale().getProduct().getUnit_price(); }


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
