package filonenko.sales.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
//Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
@Entity //Аннотация для hibernate - класс является сущностью
public class Status implements Serializable {

    @Id //Аннотация hibernate - поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Аннотация hibernate - значения поля генерируется автоматически (аналог Auto Incerment)
    private Integer id;

    //Остальные поля таблицы (String ~ varchar; Integer ~ int)
    private String name;

    public Status(String name) {
        this.name = name;
    }

    public Status() {
    }


}