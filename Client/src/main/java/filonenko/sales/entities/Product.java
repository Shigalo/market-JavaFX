package filonenko.sales.entities;

import lombok.Data;

import java.io.Serializable;

@Data //Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
public class Product implements Serializable {

    private Integer id;

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

/*    @Override
    public String toString() {
        return name;
    }*/
}
