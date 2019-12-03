package filonenko.sales.services;

import filonenko.sales.apps.Connection;
import filonenko.sales.apps.CurrentUser;
import filonenko.sales.entities.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
            if (login.equals("admin") && password.equals("admin")) {
                User user = new User("", login, password, 2);
                CurrentUser.setCurrentUser(user);
                return;
            }

            User user = new User("", login, password, 1);
            user = connection.login(user);
            if (user == null) return;
            CurrentUser.setCurrentUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registration(String login, String password, String name) {
        try {
            User user = new User(name, login, password, 0);
            user = connection.registration(user);
            if (user == null) return;
            CurrentUser.setCurrentUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editName(String newName) {
        try {
            User modifiedUser = connection.editName(CurrentUser.getCurrentUser(), newName);
            CurrentUser.setCurrentUser(modifiedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean editPassword(PasswordField password, PasswordField passwordConfirm, Alert alert) {
        try {
            if (password.getText().equals(CurrentUser.getCurrentUser().getPassword())) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void remove() {
        try {
            User user = CurrentUser.getCurrentUser();
            connection.remove(user);
            CurrentUser.setCurrentUser(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRole(User user) {
        try {
            Dialog<User> dialog = new Dialog<>();
            dialog.setTitle("Изменение доступа");

            ButtonType saveButtonType = new ButtonType("Применить", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            Label name = new Label(user.getName());
            Label login = new Label(user.getLogin());
            ChoiceBox role = new ChoiceBox<String>();
            role.getItems().addAll("Продавец", "Администратор");
            role.setValue(role.getItems().get(user.getAccess()));

            grid.add(new Label("Имя:"), 0, 0);
            grid.add(name, 1, 0);
            grid.add(new Label("Логин:"), 0, 1);
            grid.add(login, 1, 1);
            grid.add(new Label("Роль"), 0, 2);
            grid.add(role, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    try {
                        connection.setRole(user, role.getSelectionModel().getSelectedIndex());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            });
            dialog.showAndWait();
        }catch (Exception e) {e.printStackTrace();}
    }
}