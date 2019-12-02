package filonenko.sales.controllers.statistic;

import filonenko.sales.apps.Main;
import filonenko.sales.apps.MenuEventsHandler;
import filonenko.sales.entities.*;
import filonenko.sales.services.GuaranteeService;
import filonenko.sales.services.SaleService;
import filonenko.sales.services.UserService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersSales {

    public Button log;
    public Button profile;
    public Menu data;
    public Menu charts;

    public TableView<Data> table;
    public TableColumn<Data, String> seller;
    public TableColumn<Data, Double> cost;
    public TableColumn<Data, Integer> selling;
    public TableColumn<Data, Integer> refund;
    public TableColumn<Data, Integer> reselling;
    public TableColumn<Data, Integer> repairs;
    private ObservableList<Data> sales = FXCollections.observableArrayList();
    public Button chart;
    public Button back;

    @FXML
    private void initialize() throws Exception {
        MenuEventsHandler.eventHandlers(data, charts, log, profile);
        thisEventHandlers();
        seller.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().seller.getName()));
        cost.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().cost).asObject());
        selling.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().selling).asObject());
        refund.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().refund).asObject());
        reselling.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().reselling).asObject());
        repairs.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().repairs).asObject());
        table.setMaxHeight(200);
        tableUpdate();
    }

    private void thisEventHandlers() {
//        back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> goBack());
//        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> show());
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
                    default: userDataMap.get(user).cost += guarantee.getSale().getQuantity() * guarantee.getSale().getProduct().getUnit_price();
                }
            }
            for(User user : userDataMap.keySet()) {
                System.out.println(user);
                Data data = userDataMap.get(user);
                sales.addAll(data);
                System.out.println(data.cost);
                System.out.println(data.seller);
                System.out.println(data.selling);
                System.out.println(data.reselling);
                System.out.println(data.refund);
                System.out.println(data.repairs);
                table.setItems(sales);

            }
            table.setItems(sales);
            table.setPrefHeight(50+guaranteeList.size()*25);
        } catch (Exception e) { e.printStackTrace(); }
    }

   /* private void goBack() {
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
        Main.primaryStage.show();*
}*/

   class Data {

       public Data(User seller) {
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
