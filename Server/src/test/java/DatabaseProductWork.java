import filonenko.sales.dao.ProductDAO;
import filonenko.sales.entities.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class DatabaseProductWork {

    private ProductDAO ProductdataDao = null;


    @Before
    public void getDAO() { ProductdataDao = ProductDAO.getInstance(); }

    @After
    public void getList() {
        for (Product Product : ProductdataDao.findAll()) {
            System.out.println(Product);
        }
    }

    @Test
    public void createTest() {
        Product test_Product = new Product("test_Product", "test_firm", 1.0);
        ProductdataDao.create(test_Product);
    }

    @Test
    public void updateTest() {
        for(Product test_Product : ProductdataDao.findByName("test_Product")) {
            test_Product.setFirm("update_firm_test");
            ProductdataDao.update(test_Product);
        }
    }

    @Test
    public void deleteTest() {
        ProductdataDao.deleteByName("test_Product");
    }

    @Test
    public void getFirst() {
        System.out.println("first: " + ProductdataDao.findAll().get(0));
    }
}
