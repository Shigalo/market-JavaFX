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
        return dao.login(user.getLogin(), user.getPassword());
    }

    public static User registration(User user) {
        if(dao.findByLogin(user.getLogin()) != null) return null;
        return dao.registration(user.getLogin(), user.getPassword(), user.getName());
    }

    public static User editName(User user, String newName) {
        return dao.editName(user.getId(), newName);
    }

    public static User editPassword(User user, String newPassword) {
        return dao.editPassword(user.getId(), newPassword);
    }

    public static void remove(User user) {
        dao.delete(user);
    }
}
