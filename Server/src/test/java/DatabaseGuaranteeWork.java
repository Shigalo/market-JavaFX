import filonenko.sales.dao.GuaranteeDAO;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.services.SaleService;
import filonenko.sales.services.StatusService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatabaseGuaranteeWork {

    private GuaranteeDAO guaranteeDAO = null;


    @Before
    public void getDAO() { guaranteeDAO = GuaranteeDAO.getInstance(); }

    @After
    public void getList() {
        for (Guarantee guarantee : guaranteeDAO.findAll()) {
            System.out.println(guarantee);
        }
    }

    @Test
    public void createTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("10-10-2010", formatter);
        Guarantee test_Guarantee = new Guarantee(localDate);
        test_Guarantee.setSale(SaleService.getAllSales().get(0));
//        Set<Status> statuses = new HashSet<>();
//        statuses.add(StatusService.getAllStatuses().get(0));
        test_Guarantee.setStatus(StatusService.getAllStatuses().get(0));
        guaranteeDAO.create(test_Guarantee);
    }

    @Test
    public void deleteTest() {
        Guarantee guarantee = guaranteeDAO.findAll().get(0);
        guaranteeDAO.deleteByID(guarantee.getId());
    }

    @Test
    public void getFirst() {
        System.out.println("first: " + guaranteeDAO.findAll().get(0));
    }
}
