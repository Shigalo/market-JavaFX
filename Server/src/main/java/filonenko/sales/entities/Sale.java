package filonenko.sales.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
//Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
@Entity //Аннотация для hibernate - класс является сущностью
public class Sale implements Serializable {

    @Id //Аннотация hibernate - поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Аннотация hibernate - значения поля генерируется автоматически (аналог Auto Incerment)
    private Integer id;

    //Остальные поля таблицы (String ~ varchar; Integer ~ int)
    private LocalDate date;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private User user;
    //хз какие хар-ки.....


    public Sale() {
    }

    public Sale(LocalDate date, Integer quantity, Product product, User user) {
        this.date = date;
        this.quantity = quantity;
        this.product = product;
        this.user = user;
    }

    public Sale(LocalDate date, Integer quantity) {
        this.date = date;
        this.quantity = quantity;
    }
}