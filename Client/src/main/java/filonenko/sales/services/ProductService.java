package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.entities.Product;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter @Setter
public class ProductService {

    private static List<Product> ProductList;
    private static Connection connection = Connection.getInstance();

    public static List<Product> getAllProducts() throws IOException, ClassNotFoundException {
        return connection.getProductList();
    }


   /* public static void addNewProduct(String login, String password, String name) {
        try {
            Product Product = new Product(name, login, password, 0);
            Product = connection.registration(Product);
            if (Product == null) return;
            CurrentProduct.setCurrentProduct(Product);
        } catch (Exception ignored) {}
    }*/

    /*public static void editName(String newName) {
        try {
            Product modifiedProduct = connection.editName(CurrentProduct.getCurrentProduct(), newName);
            CurrentProduct.setCurrentProduct(modifiedProduct);
        } catch (Exception ignored) {}
    }*/
}