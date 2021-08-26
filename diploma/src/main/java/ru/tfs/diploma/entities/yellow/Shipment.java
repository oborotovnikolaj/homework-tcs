package ru.tfs.diploma.entities.yellow;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import ru.tfs.diploma.entities.BaseRecord;

import javax.persistence.*;

@Entity
@Table(name = "Shipment")
@Getter
@Setter
@Audited
public class Shipment extends BaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_id_seq")
    @SequenceGenerator(name = "shipment_id_seq", sequenceName = "shipment_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orders_id", nullable = false)
    private Order order;

    @Column(name = "ADDRESS")
    private String address;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

}
