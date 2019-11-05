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

    public static boolean verification(TextField login, PasswordField password, PasswordField passwordConfirm, TextField name, Alert alert) {

        Pattern p = Pattern.compile("^[a-zA-Zа-яА-Я]{2,10}");
        Matcher m = p.matcher(name.getText());
        if (!m.matches()) {
            alert.setContentText("Недопустимое имя!\n" +
                    "Требования к имени:\n" + (char)187 +
                    " Длинна 2-10 символов\n" + (char)187 +
                    " Содержат только буквы латинского алфавита");
            login.setText("");
            return false;
        }
        p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{3,15}");
        m = p.matcher(login.getText());
        if (!m.matches()) {
            alert.setContentText("Недопустимый логин!\n" +
                    "Требования к логину:\n" + (char)187 +
                    " Длинна 4-16 символов\n" + (char)187 +
                    " Содержит только цифры и буквы латинского алфавита\n" + (char)187 +
                    " Начинаутся с буквы");
            login.setText("");
            return false;
        }
        p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{7,15}");
        m = p.matcher(password.getText());
        if (!m.matches()) {
            alert.setContentText("Недопустимый пароль!\n" +
                    "Требования к паролю:\n" + (char)187 +
                    " Длинна 8-16 символов\n" + (char)187 +
                    " Содержит только цифры и буквы латинского алфавита");
            password.setText("");
            return false;
        }
        if (!password.getText().equals(passwordConfirm.getText())) {
            alert.setContentText("Ошибка подтверждения пароля!");
            passwordConfirm.setText("");
            return false;
        }
        return true;
    }
}