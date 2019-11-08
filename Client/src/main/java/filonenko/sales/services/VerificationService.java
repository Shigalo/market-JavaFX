package filonenko.sales.services;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationService {

    private static boolean loginVerification(TextField login, Alert alert) {
        Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{3,15}");
        Matcher m = p.matcher(login.getText());
        if (m.matches()) return true;
        alert.setContentText("Недопустимый логин!\n" +
                "Требования к логину:\n" + (char)187 +
                " Длинна 4-16 символов\n" + (char)187 +
                " Содержит только цифры и буквы латинского алфавита\n" + (char)187 +
                " Начинаутся с буквы");
        login.setText("");
        return false;

    }

    public static boolean passwordVerification(PasswordField password, Alert alert) {
        Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{7,15}");
        Matcher m = p.matcher(password.getText());
        if(m.matches()) return true;
        alert.setContentText("Недопустимый пароль!\n" +
                "Требования к паролю:\n" + (char)187 +
                " Длинна 8-16 символов\n" + (char)187 +
                " Содержит только цифры и буквы латинского алфавита");
        return false;


    }

    private static boolean nameVerification(TextField name, Alert alert) {
        Pattern p = Pattern.compile("^[a-zA-Zа-яА-Я]{2,10}");
        Matcher m = p.matcher(name.getText());
        if (m.matches()) return true;
        alert.setContentText("Недопустимое имя!\n" +
                "Требования к имени:\n" + (char)187 +
                " Длинна 2-10 символов\n" + (char)187 +
                " Содержат только буквы латинского алфавита");
        name.setText("");
        return false;
    }

    public static boolean passwordConfirm(PasswordField password, PasswordField passwordConfirm, Alert alert) {
        if (password.getText().equals(passwordConfirm.getText())) return true;
        alert.setContentText("Ошибка подтверждения пароля!");
        passwordConfirm.setText("");
        return false;
    }

    public static boolean fullVerification(TextField login, PasswordField password, PasswordField passwordConfirm, TextField name, Alert alert) {
        return nameVerification(name, alert) &&
                loginVerification(login, alert) &&
                passwordVerification(password, alert) &&
                passwordConfirm(password, passwordConfirm, alert);
    }
}
