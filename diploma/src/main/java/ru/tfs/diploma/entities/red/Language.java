package ru.tfs.diploma.entities.red;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.entities.BaseRecord;

import javax.persistence.*;

@Entity
@Table(name = "Language")
@Getter
@Setter
public class Language extends BaseRecord <Language> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_id_seq")
    @SequenceGenerator(name = "language_id_seq", sequenceName = "language_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

//    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Info> infos;

}
