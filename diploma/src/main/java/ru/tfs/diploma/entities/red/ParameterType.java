package ru.tfs.diploma.entities.red;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.entities.BaseRecord;

import javax.persistence.*;

@Entity
@Table(name = "ParameterType")
@Getter
@Setter
public class ParameterType extends BaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parametertype_id_seq")
    @SequenceGenerator(name = "parametertype_id_seq", sequenceName = "parametertype_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "parameterType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Parameter> parameters;

}
