package filonenko.sales.services;

import filonenko.sales.dao.ProductDAO;
import filonenko.sales.entities.Product;

import java.util.ArrayList;

public class ProductService {

    private static ArrayList<Product> productList;
    private static ProductDAO dao = ProductDAO.getInstance();

    public static ArrayList<Product> getAllProducts() {
        productList = new ArrayList<>(dao.findAll());
        return productList;
    }

    public static Product editProduct(Product product, String newName, String newFirm) {
        return dao.editProduct(product.getId(), newName, newFirm);
    }

    public static void deleteProduct(Product product) {
        dao.delete(product);
    }
}
