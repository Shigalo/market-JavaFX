package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class SaleService {

    private static List<Sale> saleList;
    private static Connection connection = Connection.getInstance();

    public static List<Sale> getAllSales() throws IOException, ClassNotFoundException {
        return connection.getSalesList();
    }

    /*public static void editProduct(Product selectedProduct) {
        try {
            Dialog<Sale> dialog = new Dialog<>();
            dialog.setTitle("Изменение родукта");

            ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField name = new TextField();
            name.setText(selectedProduct.getName());
            TextField firm = new TextField();
            firm.setText(selectedProduct.getFirm());

            grid.add(new Label("Наименование:"), 0, 0);
            grid.add(name, 1, 0);
            grid.add(new Label("Фирма:"), 0, 1);
            grid.add(firm, 1, 1);
            dialog.getDialogPane().setContent(grid);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат измениния");
            alert.setHeaderText(null);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    try {
                        Product product = connection.editProduct(selectedProduct, name.getText(), firm.getText());
                        if(product != null) alert.setContentText("Успешно");
                        else alert.setContentText("Продукт с таким наименованием уже есть в базе");
                        alert.showAndWait();
                        return product;
                    } catch (Exception ignored) {}
                }
                return null;
            });
            dialog.showAndWait();
        }
        catch (Exception ignored) { }

    }*/

   /* public static void deleteProduct(Product selectedProduct) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение удаления");
        alert.setHeaderText(null);
        alert.setContentText("Уверены, что хотите удалить данный продукт?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try { connection.deleteProduct(selectedProduct); } catch (IOException ignored) { }
        }
    }*/

    /*public static void addProduct() {
        try {
            Dialog<Product> dialog = new Dialog<>();
            dialog.setTitle("Добавление родукта");

            ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField name = new TextField();
            TextField firm = new TextField();

            TextField price = new TextField();
            price.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*\\.?\\d{0,2}")) {
                    price.setText(newValue.replaceAll("[^\\d.]", ""));
                }
                else {
                    if (!newValue.matches("\\d+\\.?\\d*")) {
                        String text = price.getText();
                        price.setText("0" + text);
                    }
                }
                if (newValue.matches("\\d*\\.{1,2}\\d{3,}")) {
                    String text = price.getText();
                    price.setText(text.substring(0, text.length() - 2) + text.substring(text.length() - 1));
                }
                if(oldValue.contains(".") && newValue.replaceFirst("\\.", "").contains(".")) {
                    price.setText(oldValue);
                }
            });

            grid.add(new Label("Наименование:"), 0, 0);
            grid.add(name, 1, 0);
            grid.add(new Label("Фирма:"), 0, 1);
            grid.add(firm, 1, 1);
            grid.add(new Label("Фирма:"), 0, 1);
            grid.add(price, 1, 2);
            dialog.getDialogPane().setContent(grid);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат добавления");
            alert.setHeaderText(null);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    try {
                        Product product = new Product(name.getText(), firm.getText(), Double.parseDouble(price.getText()));
                        product = connection.addProduct(product);
                        if(product != null) alert.setContentText("Успешно");
                        else alert.setContentText("Продукт с таким наименованием уже есть в базе");
                        alert.showAndWait();
                        return product;
                    } catch (Exception ignored) {}
                }
                return null;
            });
            dialog.showAndWait();
        }
        catch (Exception ignored) { }
    }*/
}