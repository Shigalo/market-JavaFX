import filonenko.sales.dao.StatusDAO;
import filonenko.sales.entities.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseStatusWork {

    private StatusDAO statusDAO = null;


    @Before
    public void getDAO() { statusDAO = StatusDAO.getInstance(); }

    @After
    public void getList() {
        for (Status status : statusDAO.findAll()) {
            System.out.println(status);
        }
    }

    @Test
    public void createTest() {
        Status test_Status = new Status("test_Status");
        statusDAO.create(test_Status);
    }

    @Test
    public void deleteTest() {
        statusDAO.deleteByName("test_Status");
    }

    @Test
    public void getFirst() {
        System.out.println("first: " + statusDAO.findAll().get(0));
    }
}
