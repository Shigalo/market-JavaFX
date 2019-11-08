package filonenko.sales.entities;

import lombok.Data;

import java.io.Serializable;


@Data
public class User implements Serializable {
    private Integer id;

    //Остальные поля таблицы (String ~ varchar; Integer ~ int)
    private String name;
    private String login;
    private String password;
    private Integer access;

    public User(String name, String login, String password, Integer access) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.access = access;
    }

    public User() {
    }
}
