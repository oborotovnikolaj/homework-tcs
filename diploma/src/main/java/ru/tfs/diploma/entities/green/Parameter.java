package ru.tfs.diploma.entities.green;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.entities.BaseRecord;
import ru.tfs.diploma.entities.red.ParameterType;

import javax.persistence.*;

@Entity
@Table(name = "Parameter")
@Getter
@Setter
public class Parameter extends BaseRecord<Parameter> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameter_id_seq")
    @SequenceGenerator(name = "parameter_id_seq", sequenceName = "parameter_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALUE")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parametertype_id", nullable = false)
    private ParameterType parameterType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
