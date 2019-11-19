package filonenko.sales.entities;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data //Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
public class Guarantee implements Serializable {

    private Integer id;
    private Sale sale;
    private Status status;
    private LocalDate date;


    public Guarantee() {
    }

    public Guarantee(LocalDate date) {
        this.date = date;
    }
}
