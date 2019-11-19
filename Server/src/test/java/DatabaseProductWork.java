import filonenko.sales.dao.ProductDAO;
import filonenko.sales.entities.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseProductWork {

    private ProductDAO productdataDao = null;


    @Before
    public void getDAO() { productdataDao = ProductDAO.getInstance(); }

    @After
    public void getList() {
        for (Product Product : productdataDao.findAll()) {
            System.out.println(Product);
        }
    }

    @Test
    public void createTest() {
        Product test_Product = new Product("test_Product", "test_firm", 1.0);
        productdataDao.create(test_Product);
    }

    @Test
    public void updateTest() {
        for(Product test_Product : productdataDao.findByName("test_Product")) {
            test_Product.setFirm("update_firm_test");
            productdataDao.update(test_Product);
        }
    }

    @Test
    public void deleteTest() {
        productdataDao.deleteByName("test_Product");
    }

    @Test
    public void getFirst() {
        System.out.println("first: " + productdataDao.findAll().get(0));
    }
}
