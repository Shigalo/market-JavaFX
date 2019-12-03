package filonenko.sales.services;

import filonenko.sales.dao.DAOInterface;
import filonenko.sales.dao.ProductDAO;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Storage;

import java.util.List;

public class ProductService {

    private static DAOInterface<Product> dao = ProductDAO.getInstance();

    public static List<Product> getAllProducts() {
        return dao.findAll();
    }

    public static Product editProduct(Product product, String newName, String newFirm, Double newCost) {
        return ProductDAO.getInstance().editProduct(product.getId(), newName, newFirm, newCost);
    }

    public static void deleteProduct(Product product) {
        dao.delete(product);
    }

    public static Product addProduct(Product newProduct) {
        try {
            dao.create(newProduct);
            StorageService.addStorage(new Storage(newProduct, 0));
            return dao.findById(newProduct.getId()).get();
        } catch (Exception ignored) { return null; }
    }
}
