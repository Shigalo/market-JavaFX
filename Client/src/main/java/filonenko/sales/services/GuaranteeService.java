package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.entities.Guarantee;
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
public class GuaranteeService {

    private static List<Guarantee> GuaranteeList;
    private static Connection connection = Connection.getInstance();

    public static Guarantee getGuarantee(Sale sale) throws IOException, ClassNotFoundException {
        return connection.getGuarantee(sale);
    }
}