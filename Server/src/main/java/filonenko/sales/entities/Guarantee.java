package filonenko.sales.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data //Автоматическая генерация get и set методов для всех полей класса (плагин Lombok), некоторых конструкторов, методов toString(), equals(), hashCode()
@Entity //Аннотация для hibernate - класс является сущностью
public class Guarantee implements Serializable {

    @Id //Аннотация hibernate - поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Аннотация hibernate - значения поля генерируется автоматически (аналог Auto Incerment)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_id")
    private Sale sale;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "status_id")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    private LocalDate date;


    public Guarantee() {
    }

    public Guarantee(LocalDate date) {
        this.date = date;
    }
}
