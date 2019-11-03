package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.apps.CurrentUser;
import filonenko.sales.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter @Setter
public class UserService {

    private static List<User> userList;
    private static Connection connection = Connection.getInstance();

    public static List<User> getAllUsers() throws IOException, ClassNotFoundException {
        return connection.getUserList();
    }

    public static void login(String login, String password) {
        try {
            User user = new User("", login, password, 0);
            user = connection.login(user);
            if (user == null) return;
            CurrentUser.setCurrentUser(user);
        } catch (Exception ignored) {}
    }
}