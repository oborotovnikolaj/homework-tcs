package ru.tfs.diploma.entities.yellow;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import ru.tfs.diploma.entities.BaseRecord;
import ru.tfs.diploma.entities.grey.Client;

import javax.persistence.*;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Audited
public class Order extends BaseRecord<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "client_id", nullable = true)
    @Audited(targetAuditMode = NOT_AUDITED)
    private Client client;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "currency_id", nullable = false)
//    @Audited(targetAuditMode = NOT_AUDITED)
//    private Currency currency;


//    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Payment payment;
//
//    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Shipment shipment;
//
//    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<OrderItem> orderItems;

}
