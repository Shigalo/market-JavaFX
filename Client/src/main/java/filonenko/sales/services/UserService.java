package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.apps.CurrentUser;
import filonenko.sales.entities.User;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
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
            User user = new User("", login, password, 1);
            user = connection.login(user);
            if (user == null) return;
            CurrentUser.setCurrentUser(user);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void registration(String login, String password, String name) {
        try {
            User user = new User(name, login, password, 0);
            user = connection.registration(user);
            if (user == null) return;
            CurrentUser.setCurrentUser(user);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void editName(String newName) {
        try {
            User modifiedUser = connection.editName(CurrentUser.getCurrentUser(), newName);
            CurrentUser.setCurrentUser(modifiedUser);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static boolean editPassword(PasswordField password, PasswordField passwordConfirm, Alert alert) {
        try {
            if(password.getText().equals(CurrentUser.getCurrentUser().getPassword())) {
                alert.setContentText("Пароль не может быть изменён на текущий!");
                return false;
            }
            if (VerificationService.passwordVerification(password, alert) &&
                    VerificationService.passwordConfirm(password, passwordConfirm, alert)) {
                alert.setContentText("Успешно!");
                User modifiedUser = connection.editPassword(CurrentUser.getCurrentUser(), password.getText());
                CurrentUser.setCurrentUser(modifiedUser);
                return true;
            }
            return false;
        }
        catch (Exception e) { e.printStackTrace();
        return false; }
    }

    public static void remove() {
        try {
            User user = CurrentUser.getCurrentUser();
            connection.remove(user);
            CurrentUser.setCurrentUser(null);
        } catch (Exception e) { e.printStackTrace(); }
    }
}