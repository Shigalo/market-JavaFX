import filonenko.sales.dao.UserDAO;
import filonenko.sales.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class DatabaseUserWork {

    private UserDAO userdataDao = null;


    @Before
    public void getDAO() { userdataDao = UserDAO.getInstance(); }

    @After
    public void getList() {
        for (User user : userdataDao.findAll()) {
            System.out.println(user);
        }
    }

    @Test
    public void createAdmin() { //права доступа ещё не настроены
        Scanner scanner = new Scanner(System.in);
        System.out.println("name: ");
        String name = scanner.next();
        System.out.println("login: ");
        String login = scanner.next();
        System.out.println("password: ");
        String password = scanner.next();
        User user = new User(name, login, password, 1);
        userdataDao.create(user);
    }

    @Test
    public void createTest() {
        User test_user = new User("test_user", "test_login", "test_password", 0);
        userdataDao.create(test_user);
    }

    @Test
    public void updateTest() {
        for(User test_user : userdataDao.findByName("test_user")) {
            test_user.setLogin("update_login_test");
            userdataDao.update(test_user);
        }
    }

    @Test
    public void deleteTest() {
        userdataDao.deleteByName("test_user");
    }

    @Test
    public void getFirst() {
        System.out.println("first: " + userdataDao.findAll().get(0));
    }
}
