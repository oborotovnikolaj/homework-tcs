package ru.tfs.diploma.entities.green;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.entities.BaseRecord;
import ru.tfs.diploma.entities.red.Language;

import javax.persistence.*;

@Entity
@Table(name = "info")
@Getter
@Setter
public class Info extends BaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "info_id_seq")
    @SequenceGenerator(name = "info_id_seq", sequenceName = "info_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITTLE")
    private String tittle;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

}
