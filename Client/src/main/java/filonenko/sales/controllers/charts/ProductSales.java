package filonenko.sales.controllers.charts;

import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import filonenko.sales.services.SaleService;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.*;

import static filonenko.sales.controllers.statistic.ProductSales.selectedProduct;

public class ProductSales {
    public Button log;
    public Button profile;
    public MenuBar menuBar;

    public Label product;
    public LineChart salesLineChart;
    public Button back;
    public RadioButton QSort;
    public RadioButton CSort;
    public CheckBox split;

    @FXML
    private void initialize() throws Exception {
        MediatorEventsHandler.eventHandlers(menuBar, log, profile);
        thisEventHandlers();
        ToggleGroup group = new ToggleGroup();
        QSort.setToggleGroup(group);
        CSort.setToggleGroup(group);
        QSort.setOnAction(event -> chartUpdate());
        CSort.setOnAction(event -> chartUpdate());
        split.setOnAction(event -> chartUpdate());
        QSort.setSelected(true);
        Product selectedProduct = filonenko.sales.controllers.statistic.ProductSales.selectedProduct;
        salesLineChart.setTitle("График продаж продукта " + selectedProduct.getName() + " : " + selectedProduct.getFirm());
        chartUpdate();
    }

    private void thisEventHandlers() {
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> MediatorEventsHandler.changeScene("statistic/productSales"));
    }

    private void chartUpdate() {
        try {
            List<Sale> saleList = SaleService.getSales(selectedProduct);
            saleList.sort(Comparator.comparing(Sale::getDate));
            salesLineChart.getData().clear();
            if (split.isSelected()) {
                Map<Integer, ArrayList<Sale>> map = new HashMap<>();
                for (Sale sale : saleList) {
                    Integer key = sale.getUser().getId();
                    if (!map.containsKey(key)) map.put(key, new ArrayList<>());
                    map.get(key).add(sale);
                }
                for (ArrayList<Sale> list : map.values()) {
                    XYChart.Series<String, Double> userSeries = new XYChart.Series<>();
                    userSeries.setName(list.get(0).getUser().getName());
                    if (QSort.isSelected()) for (Sale sale : list) {
                        boolean found = false;
                        int i = 0;
                        for (; i < userSeries.getData().size(); i++) {
                            if (userSeries.getData().get(i).getXValue().equals(String.valueOf(sale.getDate()))) {
                                found = true;
                                break;
                            }
                        }
                        if (found)
                            userSeries.getData().get(i).setYValue(userSeries.getData().get(i).getYValue() + sale.getQuantity());
                        else {
                            userSeries.getData().add(new XYChart.Data<>(String.valueOf(sale.getDate()), Double.valueOf(sale.getQuantity())));
                        }
                        salesLineChart.getYAxis().setLabel("Количество");
                    }
                    if (CSort.isSelected()) for (Sale sale : list) {
                        boolean found = false;
                        int i = 0;
                        for (; i < userSeries.getData().size(); i++) {
                            if (userSeries.getData().get(i).getXValue().equals(String.valueOf(sale.getDate()))) {
                                found = true;
                                break;
                            }
                        }
                        if (found)
                            userSeries.getData().get(i).setYValue(userSeries.getData().get(i).getYValue() + sale.getQuantity() * sale.getProduct().getUnit_price());
                        else
                            userSeries.getData().add(new XYChart.Data<>(String.valueOf(sale.getDate()), sale.getQuantity() * sale.getProduct().getUnit_price()));
                        salesLineChart.getYAxis().setLabel("Стоимость");
                    }
                    salesLineChart.getData().add(userSeries);
                }
                salesLineChart.setLegendVisible(true);
            } else {
                XYChart.Series<String, Double> series = new XYChart.Series<>();
                if (QSort.isSelected()) for (Sale sale : saleList) {
                    boolean found = false;
                    int i = 0;
                    for (; i < series.getData().size(); i++) {
                        if (series.getData().get(i).getXValue().equals(String.valueOf(sale.getDate()))) {
                            found = true;
                            break;
                        }
                    }
                    if (found)
                        series.getData().get(i).setYValue(series.getData().get(i).getYValue() + sale.getQuantity());
                    else {
                        series.getData().add(new XYChart.Data<>(String.valueOf(sale.getDate()), Double.valueOf(sale.getQuantity())));
                    }
                    salesLineChart.getYAxis().setLabel("Количество");
                }
                if (CSort.isSelected()) for (Sale sale : saleList) {
                    boolean found = false;
                    int i = 0;
                    for (; i < series.getData().size(); i++) {
                        if (series.getData().get(i).getXValue().equals(String.valueOf(sale.getDate()))) {
                            found = true;
                            break;
                        }
                    }
                    if (found)
                        series.getData().get(i).setYValue(series.getData().get(i).getYValue() + sale.getQuantity() * sale.getProduct().getUnit_price());
                    else
                        series.getData().add(new XYChart.Data<>(String.valueOf(sale.getDate()), sale.getQuantity() * sale.getProduct().getUnit_price()));
                    salesLineChart.getYAxis().setLabel("Стоимость");
                }
                salesLineChart.getData().addAll(series);
                salesLineChart.setLegendVisible(false);
            }
            salesLineChart.getXAxis().setLabel("Дата");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

