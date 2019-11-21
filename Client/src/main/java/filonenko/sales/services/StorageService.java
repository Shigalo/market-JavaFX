package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Storage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter @Setter
public class StorageService {

    private static List<Storage> storageList;
    private static Connection connection = Connection.getInstance();

    public static List<Storage> getStorage() throws IOException, ClassNotFoundException {
        return connection.getStorage();
    }

    public static void replenish(Storage storage) {
        try {
            Dialog<Product> dialog = new Dialog<>();
            dialog.setTitle("Пополнение запаса");

            ButtonType saveButtonType = new ButtonType("Далее", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);
            dialog.getDialogPane().lookupButton(saveButtonType).setDisable(true);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            Label name = new Label(storage.getProduct().getName());
            Label price = new Label(String.valueOf(storage.getProduct().getUnit_price()));
            Label firm = new Label(storage.getProduct().getFirm());
            TextField quantity = new TextField("0");
            quantity.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) { quantity.setText(newValue.replaceAll("[^\\d]", "")); }
                dialog.getDialogPane().lookupButton(saveButtonType).setDisable(quantity.getText().isEmpty());
            });

            grid.add(new Label("Наименование:"), 0, 0);
            grid.add(name, 1, 0);
            grid.add(new Label("Фирма:"), 0, 1);
            grid.add(firm, 1, 1);
            grid.add(new Label("Цена за ед.:"), 0, 2);
            grid.add(price, 1, 2);
            grid.add(new Label("Пополнить на на:"), 0, 3);
            grid.add(quantity, 1, 3);
            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    try {
                        connection.replenish(storage, quantity.getText());
                    } catch (Exception e) { e.printStackTrace(); }
                }
                return null;
            });
            dialog.showAndWait();
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}