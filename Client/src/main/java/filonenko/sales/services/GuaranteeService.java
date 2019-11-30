package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import filonenko.sales.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class GuaranteeService {

    private static List<Guarantee> GuaranteeList;
    private static Connection connection = Connection.getInstance();

    public static Guarantee getGuarantee(Sale sale) {
        try {
            return connection.getGuarantee(sale);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void update(Guarantee guarantee) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        switch (guarantee.getStatus().getId()) {
            case 2: alert.setContentText("Гарантия закончилась " + guarantee.getDate()); break;
            case 3: alert.setContentText("Произведена замена " + guarantee.getDate()); break;
            case 4: alert.setContentText("Возвращено по гарантии " + guarantee.getDate()); break;
            case 5: alert.setContentText("Произведён ремонт " + guarantee.getDate()); break;
            case 1: {
                Dialog<Product> dialog = new Dialog<>();
                dialog.setTitle("Обращение по гарантии");

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

                Label sale = new Label(String.valueOf(guarantee.getSale().getId()));
                Label date = new Label(String.valueOf(guarantee.getSale().getDate()));
                ChoiceBox status = new ChoiceBox<String>();
                status.getItems().addAll("Замена", "Возврат", "Ремонт");
                status.setValue(status.getItems().get(0));

                grid.add(new Label("Id продажи:"), 0, 0);
                grid.add(sale, 1, 0);
                grid.add(new Label("Дата продажи:"), 0, 1);
                grid.add(date, 1, 1);
                grid.add(new Label("Изменить статус на:"), 0, 2);
                grid.add(status, 1, 2);

                dialog.getDialogPane().setContent(grid);
                alert.setTitle("Результат измениния");
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == saveButtonType) {
                        try {
                            Guarantee value = connection.guaranteeUpdate(guarantee, status.getSelectionModel().getSelectedIndex());
                            if(value != null) alert.setContentText("Успешно");
                            else alert.setContentText("Произошла ошибка!");
                            alert.showAndWait();
                        } catch (Exception e) {
                            alert.setContentText("Произошла ошибка!");
                            e.printStackTrace();}
                    }
                    return null;
                });
                dialog.showAndWait();
            }
        }
        if(guarantee.getStatus().getId() != 1) alert.showAndWait();
    }
}