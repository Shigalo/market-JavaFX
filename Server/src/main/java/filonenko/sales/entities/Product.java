package filonenko.sales.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data //Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
@Entity //Аннотация для hibernate - класс является сущностью
public class Product implements Serializable {

    @Id //Аннотация hibernate - поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Аннотация hibernate - значения поля генерируется автоматически (аналог Auto Incerment)
    private Integer id;

    //Остальные поля таблицы (String ~ varchar; Integer ~ int)
    private String name;
    private String firm;
    private Double unit_price;
    //хз какие хар-ки.....


    public Product() {
    }

    public Product(String name, String firm, Double unit_price) {
        this.name = name;
        this.firm = firm;
        this.unit_price = unit_price;
    }
}
