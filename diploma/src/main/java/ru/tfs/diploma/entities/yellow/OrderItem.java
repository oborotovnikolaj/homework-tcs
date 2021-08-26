package ru.tfs.diploma.entities.yellow;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import ru.tfs.diploma.entities.BaseRecord;
import ru.tfs.diploma.entities.green.Product;

import javax.persistence.*;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Table(name = "OrderItem")
@Getter
@Setter
@Audited
public class OrderItem extends BaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderitem_id_seq")
    @SequenceGenerator(name = "orderitem_id_seq", sequenceName = "orderitem_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orders_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @Audited(targetAuditMode = NOT_AUDITED)
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;

}
