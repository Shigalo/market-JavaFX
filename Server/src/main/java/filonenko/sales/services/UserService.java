package filonenko.sales.services;

import filonenko.sales.dao.DAOInterface;
import filonenko.sales.dao.UserDAO;
import filonenko.sales.entities.User;

import java.util.Base64;
import java.util.List;

public class UserService {

    private static DAOInterface<User> dao = UserDAO.getInstance();

    public static List<User> getAllUsers() {
        return dao.findAll();
    }

    public static User login(User user) {
        String password = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
        return UserDAO.getInstance().login(user.getLogin(), password);
    }

    public static User registration(User user) {
        if(UserDAO.getInstance().findByLogin(user.getLogin()) != null) return null;
        String password = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
        return UserDAO.getInstance().registration(user.getLogin(), password, user.getName());
    }

    public static User editName(User user, String newName) {
        return UserDAO.getInstance().editName(user.getId(), newName);
    }

    public static User editPassword(User user, String newPassword) {
        String password = Base64.getEncoder().encodeToString(newPassword.getBytes());
        return UserDAO.getInstance().editPassword(user.getId(), password);
    }

    public static void remove(User user) {
        dao.delete(user);
    }

    public static void setRole(User user) {
        dao.update(user);
    }
}
