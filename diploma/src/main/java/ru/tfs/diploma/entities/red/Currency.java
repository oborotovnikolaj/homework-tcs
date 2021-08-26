package ru.tfs.diploma.entities.red;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.entities.BaseRecord;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "currency")
@Getter
@Setter
public class Currency extends BaseRecord<Currency> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_seq")
    @SequenceGenerator(name = "currency_id_seq", sequenceName = "currency_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MULTIPLIER")
    private BigDecimal multiplier;

//    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Order> orders;
//
//    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Payment> payments;


}
