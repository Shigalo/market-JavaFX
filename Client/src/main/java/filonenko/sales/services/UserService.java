package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.apps.CurrentUser;
import filonenko.sales.entities.User;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void registration(String login, String password, String name) {
        try {
            User user = new User(name, login, password, 0);
            user = connection.registration(user);
            if (user == null) return;
            CurrentUser.setCurrentUser(user);
        } catch (Exception ignored) {}
    }
}