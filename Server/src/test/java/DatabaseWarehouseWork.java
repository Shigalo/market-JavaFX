import filonenko.sales.dao.StorageDAO;
import filonenko.sales.entities.Storage;
import filonenko.sales.services.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseWarehouseWork {

    private StorageDAO warehouseDAO = null;


    @Before
    public void getDAO() { warehouseDAO = StorageDAO.getInstance(); }

    @After
    public void getList() {
        for (Storage Warehouse : warehouseDAO.findAll()) {
            System.out.println(Warehouse);
        }
    }

    @Test
    public void createTest() {
        Storage test_Warehouse = new Storage(ProductService.getAllProducts().get(0), 10);
        warehouseDAO.create(test_Warehouse);
    }

    @Test
    public void deleteTest() {
        Storage warehouse = warehouseDAO.findAll().get(0);
        warehouseDAO.delete(warehouse);
    }

    @Test
    public void getFirst() {
        System.out.println("first: " + warehouseDAO.findAll().get(0));
    }
}
