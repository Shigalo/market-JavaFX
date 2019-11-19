import filonenko.sales.dao.WarehouseDAO;
import filonenko.sales.entities.Warehouse;
import filonenko.sales.services.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseWarehouseWork {

    private WarehouseDAO warehouseDAO = null;


    @Before
    public void getDAO() { warehouseDAO = WarehouseDAO.getInstance(); }

    @After
    public void getList() {
        for (Warehouse Warehouse : warehouseDAO.findAll()) {
            System.out.println(Warehouse);
        }
    }

    @Test
    public void createTest() {
        Warehouse test_Warehouse = new Warehouse(ProductService.getAllProducts().get(0), 10);
        warehouseDAO.create(test_Warehouse);
    }

    @Test
    public void deleteTest() {
        Warehouse warehouse = warehouseDAO.findAll().get(0);
        warehouseDAO.delete(warehouse);
    }

    @Test
    public void getFirst() {
        System.out.println("first: " + warehouseDAO.findAll().get(0));
    }
}
