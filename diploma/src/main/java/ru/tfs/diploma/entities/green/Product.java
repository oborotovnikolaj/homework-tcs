package ru.tfs.diploma.entities.green;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.entities.BaseRecord;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Product")
@Getter
@Setter
public class Product extends BaseRecord {

//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Info> infos;
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Parameter> parameters;
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<OrderItem> orderItems;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

}
