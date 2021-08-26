package ru.tfs.diploma.entities.yellow;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import ru.tfs.diploma.entities.BaseRecord;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Payment")
@Getter
@Setter
@Audited
public class Payment extends BaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq")
    @SequenceGenerator(name = "payment_id_seq", sequenceName = "payment_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orders_id", nullable = false)
    private Order order;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "currency_id", nullable = false)
//    @Audited(targetAuditMode = NOT_AUDITED)
//    private Currency currency;

    @Column(name = "VALUE")
    private BigDecimal value;

    @Column(name = "CARDPAN")
    private String cardPan;
}
