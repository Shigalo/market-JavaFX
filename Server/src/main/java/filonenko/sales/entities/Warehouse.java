package filonenko.sales.entities;

import lombok.Data;

import javax.persistence.*;

@Data
//Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
@Entity //Аннотация для hibernate - класс является сущностью
public class Warehouse {

    @Id //Аннотация hibernate - поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Аннотация hibernate - значения поля генерируется автоматически (аналог Auto Incerment)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    public Warehouse(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Warehouse() {
    }
}
