package filonenko.sales.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter @Setter //Автоматическая генерация get и set методов для всех полей класса (плагин Lombok)
@Entity //Аннотация для hibernate - класс является сущностью
public class User implements Serializable {
    @Id //Аннотация hibernate - поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Аннотация hibernate - значения поля генерируется автоматически (аналог Auto Incerment)
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", access=" + access +
                '}';
    }
}
