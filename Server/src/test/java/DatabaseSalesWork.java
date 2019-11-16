import filonenko.sales.dao.SaleDAO;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;
import filonenko.sales.entities.Sale;
import filonenko.sales.services.ProductService;
import filonenko.sales.services.SaleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DatabaseSalesWork {

    private SaleDAO saledataDao = null;


    @Before
    public void getDAO() { saledataDao = SaleDAO.getInstance(); }

    @After
    public void getList() {
        for (Sale Sale : saledataDao.findAll()) {
            System.out.println(Sale);
        }
    }

    @Test
    public void createTest() { //права доступа ещё не настроены
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Product prod = ProductService.getAllProducts().get(0);
        LocalDate localDate = LocalDate.parse("10-10-2010", formatter);
        Sale sale = new Sale(localDate, 1, prod);
        sale.setProduct(prod);
        saledataDao.create(sale);
    }

    @Test
    public void updateTest() {
        for(Sale test_Sale : saledataDao.findAll()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            test_Sale.setDate(LocalDate.parse("11-11-2011", formatter));
            saledataDao.update(test_Sale);
        }
    }

    @Test
    public void deleteTest() {
        Sale sale = SaleService.getAllSales().get(0);
        saledataDao.delete(sale);
    }

    @Test
    public void getFirst() {
        System.out.println("first: " + saledataDao.findAll().get(0));
    }
}
