package filonenko.sales.services;

import filonenko.sales.dao.ProductDAO;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Storage;

import java.util.ArrayList;

public class ProductService {

    private static ArrayList<Product> productList;
    private static ProductDAO dao = ProductDAO.getInstance();

    public static ArrayList<Product> getAllProducts() {
        productList = new ArrayList<>(dao.findAll());
        return productList;
    }

    public static Product editProduct(Product product, String newName, String newFirm, Double newCost) {
        return dao.editProduct(product.getId(), newName, newFirm, newCost);
    }

    public static void deleteProduct(Product product) {
        dao.delete(product);
    }

    public static Product addProduct(Product newProduct) {
        dao.create(newProduct);
        StorageService.addStorage(new Storage(newProduct, 0));
        return dao.findById(newProduct.getId()).get();
    }
}
