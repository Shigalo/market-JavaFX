package filonenko.sales.entities;

import lombok.Data;

import java.io.Serializable;

@Data
//Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
public class Status implements Serializable {

    private Integer id;
    private String name;

    public Status(String name) {
        this.name = name;
    }

    public Status() {
    }


}