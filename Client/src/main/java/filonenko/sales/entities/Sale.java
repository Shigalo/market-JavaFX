package filonenko.sales.entities;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
//Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
public class Sale implements Serializable {

    private Integer id;
    private LocalDate date;
    private Integer quantity;
    private Product product;
    private User user;

    public Sale() { }

    public Sale(LocalDate date, Integer quantity, Product product, User user) {
        this.date = date;
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }

    public Sale(LocalDate date, Integer quantity) {
        this.date = date;
        this.quantity = quantity;
    }


}