package filonenko.sales.services;

import filonenko.sales.dao.UserDAO;
import filonenko.sales.entities.User;

import java.util.ArrayList;

public class UserService {

    private static ArrayList<User> userList;

    public static ArrayList<User> getAllUsers() {
        userList = new ArrayList<>(UserDAO.getInstance().findAll());
        return userList;
    }


}
