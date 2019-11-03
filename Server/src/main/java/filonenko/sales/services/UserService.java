package filonenko.sales.services;

import filonenko.sales.dao.UserDAO;
import filonenko.sales.entities.User;

import java.util.ArrayList;

public class UserService {

    private static ArrayList<User> userList;
    private static UserDAO dao = UserDAO.getInstance();

    public static ArrayList<User> getAllUsers() {
        userList = new ArrayList<>(dao.findAll());
        return userList;
    }


    public static User login(User user) {
        return user = dao.login(user.getLogin(), user.getPassword());
    }
}
