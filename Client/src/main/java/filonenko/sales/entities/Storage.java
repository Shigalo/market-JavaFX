package filonenko.sales.entities;

import lombok.Data;

import java.io.Serializable;

@Data
//Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
public class Storage implements Serializable {

    private Integer id;
    private Product product;
    private Integer quantity;

    public Storage(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Storage() {
    }
}
