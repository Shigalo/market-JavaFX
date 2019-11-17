package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class SaleService {

    private static List<Sale> saleList;
    private static Connection connection = Connection.getInstance();

    public static List<Sale> getAllSales() throws IOException, ClassNotFoundException {
        System.out.println("!");
        List<Sale> list = connection.getSalesList();
        System.out.println(list.getClass());
        for(Sale sale : list) {
            System.out.println("@");
            System.out.println(sale);
        }
        System.out.println("!");

        return list;
    }

    public static void deleteSale(Sale selectedSale) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение удаления");
        alert.setHeaderText(null);
        alert.setContentText("Уверены, что хотите удалить данные о продаже?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try { connection.deleteSale(selectedSale); } catch (IOException ignored) { }
        }
    }

    public static void addSale() {
        try {
            Dialog<Product> dialog = new Dialog<>();
            dialog.setTitle("Добавление родукта");

            ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);
            dialog.getDialogPane().lookupButton(saveButtonType).setDisable(true);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            ObservableList<Product> products = FXCollections.observableArrayList();
            List<Product> productList = ProductService.getAllProducts();
            products.setAll(productList);

            ChoiceBox product = new ChoiceBox(products);
            product.setValue(productList.get(0));
            DatePicker date = new DatePicker();
            TextField quantity = new TextField();

            quantity.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) { quantity.setText(newValue.replaceAll("[^\\d]", "")); }
                dialog.getDialogPane().lookupButton(saveButtonType).setDisable(quantity.getText().isEmpty() || date.getEditor().getText().isEmpty());
            });

            date.setOnAction(event -> dialog.getDialogPane().lookupButton(saveButtonType).setDisable(quantity.getText().isEmpty() || date.getEditor().getText().isEmpty()));

            grid.add(new Label("Продукт:"), 0, 0);
            grid.add(product, 1, 0);
            grid.add(new Label("Дата:"), 0, 1);
            grid.add(date, 1, 1);
            grid.add(new Label("Количество:"), 0, 2);
            grid.add(quantity, 1, 2);
            dialog.getDialogPane().setContent(grid);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат добавления");
            alert.setHeaderText(null);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        LocalDate localDate = LocalDate.parse(date.getEditor().getText(), formatter);
                        Integer quantityInt = Integer.parseInt(quantity.getText());
                        Product selectedProduct = (Product)product.getSelectionModel().getSelectedItem();
                        Sale sale = new Sale(localDate, localDate, quantityInt, selectedProduct);
                        connection.addSale(sale);
                    } catch (Exception ignored) {}
                }
                return null;
            });
            dialog.showAndWait();
        }
        catch (Exception ignored) { }
    }
}