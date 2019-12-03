package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.apps.CurrentUser;
import filonenko.sales.apps.MediatorEventsHandler;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import filonenko.sales.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
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
        return connection.getSalesList();
    }

    public static void deleteSale(Sale selectedSale) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение удаления");
        alert.setHeaderText(null);
        alert.setContentText("Уверены, что хотите удалить данные о продаже?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try { connection.deleteSale(selectedSale); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public static void addSale() {
        try {
            Dialog<Product> dialog = new Dialog<>();
            dialog.setTitle("Новая сделка");

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
            product.setConverter(new StringConverter<Product>() {
                @Override public String toString(Product object) { return object.getName(); }
                @Override public Product fromString(String string) { return null; }
            });
            product.setValue(productList.get(0));
            DatePicker date = new DatePicker(LocalDate.now());
            date.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) > 0 );
                }
            });
            TextField quantity = new TextField();

            quantity.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) { quantity.setText(newValue.replaceAll("[^\\d]", "")); }
                dialog.getDialogPane().lookupButton(saveButtonType).setDisable(ready(quantity, date));
            });

            date.setOnAction(event -> dialog.getDialogPane().lookupButton(saveButtonType).setDisable(ready(quantity, date)));

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
                        User user = CurrentUser.getCurrentUser();
                        Sale sale = new Sale(localDate, quantityInt, selectedProduct, user);
                        sale = connection.addSale(sale);
                        if(sale == null) {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Ошибка");
                            alert2.setHeaderText(null);
                            alert2.setContentText("Недостаточно продукции на складе!");
                            alert2.showAndWait();
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                }
                return null;
            });
            dialog.showAndWait();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private static boolean ready(TextField quantity, DatePicker date) {
        return quantity.getText().isEmpty() || date.getEditor().getText().isEmpty();
    }

    public static void getInfo(Sale selectedSale) {
        try {
            Dialog<Product> dialog = new Dialog<>();
            dialog.setTitle("Информация о сделке");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            Label product = new Label(selectedSale.getProduct().getName());
            Label firm = new Label(selectedSale.getProduct().getFirm());
            Label unit_price = new Label(String.valueOf(selectedSale.getProduct().getUnit_price()));
            Label quantity = new Label(String.valueOf(selectedSale.getQuantity()));
            Label cost = new Label(String.valueOf(selectedSale.getQuantity() * selectedSale.getProduct().getUnit_price()));
            Label date = new Label(String.valueOf(selectedSale.getDate()));
            Label user = new Label(selectedSale.getUser().getName());

            Guarantee guarantee = GuaranteeService.getGuarantee(selectedSale);
            Label status = new Label(guarantee.getStatus().getName());
            Label dateStatus = new Label(String.valueOf(guarantee.getDate()));

            grid.add(new Label("Продукт:"), 0, 0);
            grid.add(product, 1, 0);
            grid.add(new Label("Фирма:"), 0, 1);
            grid.add(firm, 1, 1);
            grid.add(new Label("Цена продукта:"), 0, 2);
            grid.add(unit_price, 1, 2);
            grid.add(new Label("Количество:"), 0, 3);
            grid.add(quantity, 1, 3);
            grid.add(new Label("Доход сделки:"), 0, 4);
            grid.add(cost, 1, 4);
            grid.add(new Label("Дата сделки:"), 0, 5);
            grid.add(date, 1, 5);
            grid.add(new Label("Продавец:"), 0, 6);
            grid.add(user, 1, 6);
            grid.add(new Label("Гарантия:"), 0, 7);
            grid.add(status, 1, 7);
            grid.add(new Label("Дата:"), 0, 8);
            grid.add(dateStatus, 1, 8);

            dialog.getDialogPane().setContent(grid);

            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
            closeButton.setVisible(false);
            dialog.showAndWait();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public static List<Sale> getSales(Product product) throws IOException, ClassNotFoundException {
        return connection.getProductSales(product);
    }

    public static void productStatistic(Product product) {

        try {
            if(SaleService.getSales(product).isEmpty()) throw new Exception();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Предупреждение");
            alert.setHeaderText(null);
            alert.setContentText("Записи отсутствуют");
            alert.showAndWait();
            return;
        }
        MediatorEventsHandler.changeScene("statistic/productSales");
    }
}